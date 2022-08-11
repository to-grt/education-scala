ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root =
  (project in file("."))
    .settings(
      name := "education-scala",
      scalacOptions ++= Seq("-Xcheck-macros"),
      libraryDependencies ++= Seq(
        "org.scalameta" %% "munit" % "0.7.29" % Test,
        "dev.zio"       %% "zio"   % "2.0.0"
      )
    )
