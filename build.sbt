licenses := Seq("Apache 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))

homepage := Some(url("https://github.com/ppalchikov/pp-imdg"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/ppalchikov/pp-imdg"),
    "scm:git@github.com/ppalchikov/pp-imdg.git"
  )
)

developers := List(
  Developer(
    id    = "ppalchikov",
    name  = "Pavel Palchikov",
    email = "palchikov007@gmail.com",
    url   = url("https://github.com/ppalchikov")
  )
)

organization := "com.github.ppalchikov"

name := "pp-imdg"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.2"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test"

useGpg := true
publishMavenStyle := true
publishArtifact in Test := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}