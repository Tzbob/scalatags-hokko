resolvers in ThisBuild += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

organization in ThisBuild := "be.tzbob"
scalaVersion in ThisBuild := "2.12.1"
crossScalaVersions in ThisBuild := Seq("2.11.8", "2.12.1")
version in ThisBuild := "0.3.0-SNAPSHOT"

scalacOptions in ThisBuild ++= Seq(
  "-encoding",
  "UTF-8",
  "-feature",
  "-deprecation",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-language:higherKinds"
)

lazy val publishSettings = Seq(
  homepage := Some(url("https://github.com/Tzbob/scalatags-hokko")),
  licenses := Seq(
    "MIT" -> url("https://opensource.org/licenses/mit-license.php")),
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra :=
    <scm>
      <url>git@github.com:tzbob/scalatags-hokko.git</url>
      <connection>scm:git:git@github.com:tzbob/scalatags-hokko.git</connection>
    </scm>
      <developers>
        <developer>
          <id>tzbob</id>
          <name>Bob Reynders</name>
          <url>https://github.com/tzbob</url>
        </developer>
      </developers>
)

lazy val root =
  Project(id = "root", base = file("."))
    .aggregate(hokko, examples)
    .settings(
      publish := {},
      publishLocal := {}
    )

lazy val hokko =
  Project(id = "scalatags-hokko", base = file("modules/core"))
    .settings(
      name := "scalatags-hokko",
      requiresDOM in Test := true,
      scalaJSModuleKind := ModuleKind.CommonJSModule,
      libraryDependencies ++= Seq(
        "be.tzbob"      %%% "hokko"             % "0.4.2-SNAPSHOT",
        "be.tzbob"      %%% "scala-js-snabbdom" % "0.2.0",
        "org.typelevel" %%% "cats"              % "0.9.0",
        "com.lihaoyi"   %%% "scalatags"         % "0.6.3",
        "com.lihaoyi"   %%% "utest"             % "0.4.5" % "test"
      ),
      testFrameworks += new TestFramework("utest.runner.Framework"),
      useYarn := true,
      enableReloadWorkflow := true
    )
    .enablePlugins(ScalaJSPlugin)
    .enablePlugins(ScalaJSBundlerPlugin)

lazy val examples =
  Project(id = "examples", base = file("modules/examples"))
    .settings(persistLauncher := true,
              scalaJSModuleKind := ModuleKind.CommonJSModule)
    .dependsOn(hokko)
    .enablePlugins(ScalaJSPlugin)
    .enablePlugins(ScalaJSBundlerPlugin)
