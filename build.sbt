organization in ThisBuild := "self.contained"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.11"

resolvers += "JBoss" at "https://repository.jboss.org/"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"

lazy val metaMacroSettings = Seq(
  resolvers += Resolver.sonatypeRepo("releases"),
  resolvers += Resolver.bintrayIvyRepo("scalameta", "maven"),
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M9" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
  scalacOptions in (Compile, console) := Seq() // macroparadise plugin doesn't work in repl yet.
)

lazy val lagomSettings = Seq(
  libraryDependencies ++= Seq(
    lagomScaladslPersistenceCassandra,
    macwire,
    filters
  )
)

// =================
// projects
// =================

lazy val utils = project.settings(
  lagomSettings,
  metaMacroSettings,
  libraryDependencies ++= Seq(
    lagomScaladslApi,
    lagomScaladslServer,
    "com.auth0" % "java-jwt" % "3.1.0",
    "org.scalameta" %% "scalameta" % "1.8.0"
  )
)

lazy val `user-api` = project
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
  .dependsOn(utils)

lazy val `user-impl` = project
  .enablePlugins(LagomScala)
  .settings(lagomSettings)
  .settings(lagomForkedTestSettings: _*)
  .settings(metaMacroSettings)
  .dependsOn(`user-api`, utils)

lazy val root = project
  .aggregate(
    `user-api`,
    `user-impl`,
    utils
  )
