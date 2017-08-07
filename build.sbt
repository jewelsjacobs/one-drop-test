organization in ThisBuild := "com.dropone.github"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `github` = (project in file("."))
  .aggregate(`github-api`, `github-impl`, `github-stream-api`, `github-stream-impl`)

lazy val `github-api` = (project in file("github-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `github-impl` = (project in file("github-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`github-api`)

lazy val `github-stream-api` = (project in file("github-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `github-stream-impl` = (project in file("github-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`github-stream-api`, `github-api`)

lagomCassandraCleanOnStart in ThisBuild := false

lagomUnmanagedServices in ThisBuild += ("github" -> "https://api.github.com")