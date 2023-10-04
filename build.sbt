ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / semanticdbVersion := "4.4.33"
ThisBuild / semanticdbEnabled := true
ThisBuild / scalafixOnCompile := true

lazy val root = Project("scalaPractice", file("."))
  .settings(
    name := "scalaPractice",
    idePackagePrefix := Some("com.sam0jones0.scalaPractice")
  )
  .settings(Dependencies.common)
