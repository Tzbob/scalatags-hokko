enablePlugins(ScalaJSPlugin)

scalaVersion in ThisBuild := "2.11.8"

scalafmtConfig in ThisBuild := Some(file(".scalafmt.conf"))

organization := "be.tzbob"
name := "scalatags-hokko"
version := "0.1-SNAPSHOT"

requiresDOM := true

scalacOptions ++= Seq(
  "-encoding",
  "UTF-8",
  "-target:jvm-1.6",
  "-feature",
  "-deprecation",
  "-Xlint",
  "-Yinline-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-language:higherKinds",
  "-language:existentials"
)

libraryDependencies ++= Seq(
  "be.tzbob"      %%% "hokko"     % "0.4.0-SNAPSHOT",
  "org.typelevel" %%% "cats"      % "0.7.2",
  "com.lihaoyi"   %%% "scalatags" % "0.6.0",
  "com.lihaoyi"   %%% "utest"     % "0.3.1" % "test"
)

jsDependencies ++= Seq(
  "org.webjars.bower" % "virtual-dom" % "2.1.1" / "virtual-dom.js"
)

testFrameworks += new TestFramework("utest.runner.Framework")
