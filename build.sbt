ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / semanticdbVersion := "4.4.33"
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"
ThisBuild / semanticdbEnabled := true

lazy val root = (project in file("."))
  .settings(
    name := "scalaPractice",
    idePackagePrefix := Some("com.scalaPractice")
  )
