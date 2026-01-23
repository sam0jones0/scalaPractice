//

sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String)            extends Json
final case class JsNumber(get: Double)            extends Json
final case class JsSeq(get: Seq[Json])            extends Json
final case class JsonNone()                       extends Json

// This is the typeclass itself
trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

object JsonWriter {
  // Having these in companion object means they are always in scope

  // These are instances of the typeclass
  given stringWriter: JsonWriter[String] = new JsonWriter[String] {
    override def write(value: String): Json = JsString(value)
  }

  /* Old Scala 2 way
    implicit val stringWriter: JsonWriter[String] = new JsonWriter[String] {
      override def write(value: String): Json = JsString(value)
    }
   */

  given doubleWriter: JsonWriter[Double] = new JsonWriter[Double] {
    override def write(value: Double) = JsNumber(value)
  }

  given stringOrDoubleWriter: JsonWriter[String | Double] = new JsonWriter[String | Double] {
    override def write(value: String | Double) =
      value match {
        case s: String => JsString(s)
        case d: Double => JsNumber(d)
      }
  }

  given personWriter: JsonWriter[Person] = new JsonWriter[Person] {
    override def write(value: Person): Json = JsObject(
      Map("name" -> JsString(value.name), "email" -> JsString(value.email)))
  }

  given SeqWriter[A](using elemWriter: JsonWriter[A]): JsonWriter[Seq[A]] =
    new JsonWriter[Seq[A]] {
      override def write(value: Seq[A]): Json = JsSeq(value.map(s => elemWriter.write(s)))
    }

// Impossible. You _must_ implement a writer for your type
//  given AnyWriter: JsonWriter[Any] = new JsonWriter[Any] {
//    override def write(value: Any) = value.
//  }

  given ObjectWriter[A](using elemWriter: JsonWriter[A]): JsonWriter[Map[String, A]] =
    new JsonWriter[Map[String, A]] {
      override def write(value: Map[String, A]) = JsObject {
        Map.from {
          value.map { (s, a) =>
            (s, elemWriter.write(a))
          }
        }
      }
    }

  given OptionWriter[A](using elemWriter: JsonWriter[A]): JsonWriter[Option[A]] =
    new JsonWriter[Option[A]] {
      override def write(option: Option[A]) = option match {
        case Some(value) => elemWriter.write(value)
        case None        => JsonNone()
      }
    }

}

// This is an interface
object MyJson {
  def toJson[A](value: A)(implicit writer: JsonWriter[A]): Json =
    writer.write(value)
  // So for any value A, if there is an implicit JsonWriter in scope, we can turn A to Json
}

object MyJsonSyntax {
  // This syntax could be in the companion object for JsonWriter if we wanted
  // it would then get brought into implicit scope automatically

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

import JsonWriter.given
import MyJsonSyntax.*

import scala.language.implicitConversions

// These are the same
implicitly[JsonWriter[String]]
summon[JsonWriter[String]]

//Seq(1).toJson
Seq("hello").toJson

"hi".toJson

MyJson.toJson("string")

val samJson = MyJson.toJson(Person("sam", "sam@sam.com"))

val samMapString: Map[String, String]         = Map.from(Seq("name" -> "sam", "age" -> "sam"))
val samMapInt: Map[String, Double]            = Map.from(Seq("name" -> 1337d, "age" -> 34d))
val samMapMixed: Map[String, String | Double] = Map.from(Seq("name" -> "sam", "age" -> 34d))
val maybeString: Option[String]               = Some("hey")
val maybeNotString: Option[String]            = Option.empty[String]

val mapJsonString: Json = MyJson.toJson(samMapString)
val mapJsonInt: Json    = MyJson.toJson(samMapInt)
val mapJsonMixed: Json  = MyJson.toJson(samMapMixed)

val optionalString: Json = MyJson.toJson(maybeString)
val optionalString: Json = MyJson.toJson(maybeNotString)

println(mapJsonMixed)
