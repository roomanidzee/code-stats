
val projectVersion = "0.0.1"
val projectName = "code-stats"
val scalaProjectVersion = "2.13.3"

lazy val commonSettings = Seq(
  version := projectVersion,
  scalaVersion := scalaProjectVersion,
  resolvers ++= Seq(
    Resolver.mavenCentral,
    Resolver.mavenLocal,
    Resolver.bintrayRepo("sbt-native-packager", "maven"),
    Resolver.bintrayRepo("sbt-assembly", "maven")
  ),
  scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8"),
  scalafmtOnCompile := true,
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := s"${projectName}"
  )
  .aggregate(protobuf, parquet, db)
  .dependsOn(protobuf, parquet, db)

lazy val protobuf = (project in file("modules/protobuf"))
  .settings(commonSettings)
  .settings(
    name := s"${projectName}-protobuf",
    libraryDependencies ++= Dependencies.protobufModuleDeps,
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
    PB.targets in Compile := Seq(
      scalapb.gen(javaConversions = true) -> (sourceManaged in Compile).value,
      PB.gens.java -> (sourceManaged in Compile).value
    ),
  )

lazy val parquet = (project in file("modules/parquet"))
  .settings(commonSettings)
  .settings(
    name := s"${projectName}-parquet",
    libraryDependencies ++= Dependencies.parquetModuleDeps ++ Dependencies.parquetModuleTestDeps,
    testFrameworks += new TestFramework("munit.Framework")
  )
  .aggregate(protobuf)
  .dependsOn(protobuf)

lazy val db = (project in file("modules/db"))
  .settings(commonSettings)
  .settings(
    name := s"${projectName}-db",
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies ++= Dependencies.dbModuleDeps ++ Dependencies.dbModuleTestDeps
  )

lazy val web = (project in file("modules/web"))
  .settings(commonSettings)
  .settings(
    name := s"${projectName}-web"
  )
