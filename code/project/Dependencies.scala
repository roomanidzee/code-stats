import sbt._

object Dependencies {

  private val scalapb : Seq[ModuleID] = Seq(
    "com.thesamet.scalapb" %% "scalapb-runtime" % Versions.scalapb % "protobuf"
  )

  private val monix: Seq[ModuleID] = Seq(
    "io.monix" %% "monix" % Versions.monix
  )

  private val monixParquet: Seq[ModuleID] = Seq(
    "io.monix" %% "monix-parquet" % Versions.monix_parquet
  )

  private val munit: Seq[ModuleID] = Seq(
    "org.scalameta" %% "munit" % Versions.munit % Test
  )

  private val logging: Seq[ModuleID] = Seq(
    "org.apache.logging.log4j" % "log4j-api" % Versions.log4j2,
    "org.apache.logging.log4j" % "log4j-core" % Versions.log4j2,
    "com.typesafe.scala-logging" %% "scala-logging" % Versions.scalaLogging
  )

  private val relationalDB: Seq[ModuleID] = Seq(
    "org.tpolecat" %% "doobie-core"   % Versions.doobie,
    "org.tpolecat" %% "doobie-h2"     % Versions.doobie,
    "org.tpolecat" %% "doobie-hikari" % Versions.doobie,
    "com.h2database" % "h2" % Versions.h2
  )

  private val fs2: Seq[ModuleID] = Seq(
    "co.fs2" %% "fs2-reactive-streams" % Versions.fs2
  )

  private val tethys: Seq[ModuleID] = Seq(
    "com.tethys-json" %% "tethys-core" % Versions.tethys,
    "com.tethys-json" %% "tethys-jackson" % Versions.tethys,
    "com.tethys-json" %% "tethys-derivation" % Versions.tethys,
    "com.tethys-json" %% "tethys-json4s" % Versions.tethys
  )

  private val sttp: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.client" %% "core" % Versions.sttp
  )

  private val monixClient: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.client" %% "async-http-client-backend-monix" % Versions.sttp
  ).union(sttp)

  private val pureConfig: Seq[ModuleID] = Seq(
    "com.github.pureconfig" %% "pureconfig" % Versions.pureconfig
  )

  private val wiremock: Seq[ModuleID] =
    Seq("com.github.tomakehurst" % "wiremock" % Versions.wiremock).map(_ % Test)

  val protobufModuleDeps: Seq[ModuleID] = scalapb

  val parquetModuleDeps: Seq[ModuleID] = monix.union(monixParquet).union(logging)
  val parquetModuleTestDeps: Seq[ModuleID] = munit.union(logging)

  val dbModuleDeps: Seq[ModuleID] = monix.union(logging).union(relationalDB).union(fs2)
  val dbModuleTestDeps: Seq[ModuleID] = munit.union(logging)

  val webModuleDeps: Seq[ModuleID] =
    monix.union(logging)
         .union(tethys)
         .union(monixClient)
         .union(pureConfig)

  val webModuleTestDeps: Seq[ModuleID] = munit.union(logging).union(wiremock)

}
