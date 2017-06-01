package self.contained.utils

import scala.collection.immutable.Seq
import scala.meta._


class AutoJson extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    def createFormat(name: Type.Name): Defn.Val = {
      q"""implicit val format: play.api.libs.json.Format[$name] = play.api.libs.json.Json.format"""
    }

    defn match {
      // companion object exists
      case Term.Block(
      Seq(cls @ Defn.Class(_, name, _, _, _),
      companion: Defn.Object)) =>
        val implicitFormatVal = createFormat(name)
        val templateStats: Seq[Stat] =
          implicitFormatVal +: companion.templ.stats.getOrElse(Nil)
        val newCompanion = companion.copy(
          templ = companion.templ.copy(stats = Some(templateStats)))
        Term.Block(Seq(cls, newCompanion))
      // companion object does not exists
      case cls @ Defn.Class(_, name, _, ctor, _) =>
        val implicitFormatVal = createFormat(name)
        val companion   = q"object ${Term.Name(name.value)} { $implicitFormatVal }"
        Term.Block(Seq(cls, companion))
      case _ =>
        println(defn.structure)
        abort("@AutoJson must annotate a class.")
    }
  }
}