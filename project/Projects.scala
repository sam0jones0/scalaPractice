import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile
import sbt.Keys.{name as projectName, *}
import sbt.*
import scalafix.sbt.ScalafixPlugin.autoImport.scalafixOnCompile
import sbtbuildinfo.BuildInfoPlugin.autoImport.{BuildInfoKey, buildInfoKeys, buildInfoPackage}

import scala.collection.Seq

object Projects {

  def module(moduleName: String): Project = module(moduleName, moduleName)

  def module(moduleName: String, fileName: String): Project =
    Project(moduleName, base = file(fileName))
      .settings(
        scalacOptions ++= Seq(
          "-encoding",
          "UTF-8",
          "-deprecation",
          "-unchecked",
          "-Yrangepos",
          "-Ywarn-dead-code",
          "-Ywarn-unused",
          "-release:17",
          "-feature"
        ),
        Test / parallelExecution := false,
        Test / javaOptions += "-Xmx4G",
        run / fork := true,
        Test / fork := true,
        scalafmtOnCompile := sys.env.getOrElse("RUN_SCALAFMT_ON_COMPILE", "false").toBoolean,
        scalafixOnCompile := sys.env.getOrElse("RUN_SCALAFIX_ON_COMPILE", "false").toBoolean,
        updateOptions := updateOptions.value.withGigahorse(false),
        buildInfoKeys := Seq[BuildInfoKey](projectName, version, scalaVersion, sbtVersion),
        buildInfoPackage := "com.sam0jones0.scalaPractice"

      )

}
