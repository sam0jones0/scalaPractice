//

sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String)            extends Json
final case class JsNumber(get: Double)            extends Json
final case class JsSeq(get: Seq[Json])            extends Json

// This is the typeclass itself
trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

object JsonWriterInstances {

  // These are instances of the typeclass
  given stringWriter: JsonWriter[String] = new JsonWriter[String] {
    override def write(value: String): Json = JsString(value)
  }

  given personWriter: JsonWriter[Person] = new JsonWriter[Person] {
    override def write(value: Person): Json = JsObject(
      Map("name" -> JsString(value.name), "email" -> JsString(value.email)))
  }

  given SeqWriter[A](using elemWriter: JsonWriter[A]): JsonWriter[Seq[A]] =
    new JsonWriter[Seq[A]] {
      override def write(value: Seq[A]): Json = JsSeq(value.map(s => elemWriter.write(s)))
    }

}

// This is an interface
object MyJson {
  def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json =
    writer.write(value)
}

object MyJsonSyntax {
  // Old Scala 2 way
//  implicit class MyJsonWriterOps[A](value: A) {
//    def toJson(implicit writer: JsonWriter[A]) =
//      writer.write(value)
//  }

  // Scala 3 way
  extension [A](value: A) {
    def toJson(implicit writer: JsonWriter[A]) = writer.write(value)
  }
}

import JsonWriterInstances.given
import MyJsonSyntax.*

summon[JsonWriter[String]]

//Seq(1).toJson
Seq("hello").toJson

"hi".toJson

MyJson.toJson("string")

val samJson = MyJson.toJson(Person("sam", "sam@sam.com"))
