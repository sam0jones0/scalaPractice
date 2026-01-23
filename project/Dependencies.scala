import sbt.Keys.libraryDependencies
import sbt.Keys._
import sbt._

object Dependencies {

  object Versions {
    val cats            = "2.9.0"
    val caseInsensitive = "1.4.0"
  }

  object Utils {
    val catsCore        = "org.typelevel" %% "cats-core"        % Versions.cats
    val caseInsensitive = "org.typelevel" %% "case-insensitive" % Versions.caseInsensitive

    val all = Seq(catsCore, caseInsensitive)
  }

  lazy val common = libraryDependencies ++= Utils.all
}
