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
    "org.apache.logging.log4j" % "log4j-1.2-api" % Versions.log4j2,
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

  val protobufModuleDeps: Seq[ModuleID] = scalapb

  val parquetModuleDeps: Seq[ModuleID] = monix.union(monixParquet).union(logging)
  val parquetModuleTestDeps: Seq[ModuleID] = munit.union(logging)

  val dbModuleDeps: Seq[ModuleID] = monix.union(logging).union(relationalDB).union(fs2)
  val dbModuleTestDeps: Seq[ModuleID] = munit.union(logging)

}
