organization in ThisBuild := "co.edu.eafit.dis"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.8"


// Dependencies
val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `kealearning` = (project in file("."))
  .aggregate(`kealearning-api`, `kealearning-impl`, `kealearning-stream-api`,
    `kealearning-stream-impl`, `context-api`, `context-impl`)


// Development enviroment configuration
lagomCassandraCleanOnStart in ThisBuild := true

// Micro services definition
lazy val `kealearning-api` = (project in file("kealearning-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

// Micro services implementation
lazy val `kealearning-impl` = (project in file("kealearning-impl"))
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
  .settings(lagomForkedTestSettings)
  .dependsOn(`kealearning-api`)


// u services def
lazy val `kealearning-stream-api` = (project in file("kealearning-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

// u services impl
lazy val `kealearning-stream-impl` = (project in file("kealearning-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`kealearning-stream-api`, `kealearning-api`)

// u services def
lazy val `context-api` = (project in file("context-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

// u services impl
lazy val `context-impl` = (project in file("context-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      lagomScaladslPubSub,
      macwire,
      scalaTest,
    )
  )
  .dependsOn(`context-api`)