scalaVersion in ThisBuild := "2.11.8"
scalafmtConfig in ThisBuild := Some(file(".scalafmt.conf"))

organization in ThisBuild := "be.tzbob"
version in ThisBuild := "0.1-SNAPSHOT"

scalacOptions in ThisBuild ++= Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-deprecation",
  "-Xlint",
  "-Yinline-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-language:higherKinds"
)

lazy val hokko =
  Project(id = "scalatags-hokko", base = file("modules/core"))
    .settings(
      name := "scalatags-hokko",
      requiresDOM := true,
      libraryDependencies ++= Seq(
        "be.tzbob"      %%% "hokko"     % "0.4.0-SNAPSHOT",
        "org.typelevel" %%% "cats"      % "0.7.2",
        "com.lihaoyi"   %%% "scalatags" % "0.6.0",
        "com.lihaoyi"   %%% "utest"     % "0.3.1" % "test"
      ),
      jsDependencies ++= Seq(
        "org.webjars.bower" % "virtual-dom" % "2.1.1" / "virtual-dom.js"
      ),
      testFrameworks += new TestFramework("utest.runner.Framework")
    )
    .enablePlugins(ScalaJSPlugin)

lazy val examples =
  Project(id = "examples", base = file("modules/examples"))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      persistLauncher := true
    )
    .dependsOn(hokko)
