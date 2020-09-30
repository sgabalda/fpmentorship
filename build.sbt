name := "fpmentorship"

version := "0.1"

scalaVersion := "2.12.11"

val catsVersion = "1.5.0"
val catsEffectVersion = "2.1.3" // Same version: http4s

lazy val root = (project in file("."))
  .settings(resolvers += "Stuart's Public".at(
      "http://nexus.internal.stuart.com:8081/repository/maven-public/"
    ),
    resolvers += "Stuart's Release".at(
      "http://nexus.internal.stuart.com:8081/repository/maven-releases/"
    ),    
libraryDependencies += "org.typelevel" %% "cats-core" % catsVersion,
    libraryDependencies += "org.typelevel" %% "cats-effect" % catsEffectVersion
)

