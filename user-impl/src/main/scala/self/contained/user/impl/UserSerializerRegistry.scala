package self.contained.user.impl

import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import self.contained.user.api.EmailAndPassword

object UserSerializerRegistry extends JsonSerializerRegistry {
  override def serializers = Vector(
    JsonSerializer[EmailAndPassword]
  )
}
