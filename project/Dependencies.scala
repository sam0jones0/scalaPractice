import sbt.Keys.libraryDependencies
import sbt.Keys._
import sbt._

object Dependencies {

  object Versions {
    val cats = "2.9.0"
  }

  object Utils {
    val catsCore = "org.typelevel" %% "cats-core" % Versions.cats

    val all = Seq(catsCore)
  }

  lazy val common = libraryDependencies ++= Utils.all
}
