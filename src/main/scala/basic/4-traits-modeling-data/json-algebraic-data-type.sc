/* Algebraic data type for JSON in BNF notation:

Json ::= JsNumber value:Double
       | JsString value:String
       | JsBoolean value:Boolean
       | JsNull
       | JsSequence
       | JsObject

JsSequence ::= SeqCell head:Json tail:JsSequence
             | SeqEnd

JsObject ::= ObjectCell key:String value:Json tail:JsObject
           | ObjectEnd

 */

// TODO: Why the hell does this need to be in a json object? Doesn't work without.

object json {
  sealed trait Json {
    def print: String = {

      def seqToJson(seqCell: SeqCell): String =
        seqCell match {
          case SeqCell(head, tail: SeqCell) => s"${head.print}, ${seqToJson(tail)}"
          case SeqCell(head, SeqEnd)        => s"${head.print}"
        }

      def objToJson(objectCell: ObjectCell): String =
        objectCell match {
          case ObjectCell(key, value, tail: ObjectCell) => s""""$key": ${value.print}, ${objToJson(tail)}"""
          case ObjectCell(key, value, ObjectEnd)        => s""""$key": ${value.print}"""
        }

      this match {
        case JsNumber(value)        => value.toString
        case JsString(value)        => s""""$value""""
        case JsBoolean(value)       => value.toString
        case JsNull                 => "null"
        case seqCell: SeqCell       => s"[${seqToJson(seqCell)}]"
        case objectCell: ObjectCell => s"{${objToJson(objectCell)}}"
        case SeqEnd                 => "[]"
        case ObjectEnd              => "{}"
      }
    }

  }

  // Values

  final case class JsNumber(value: Double) extends Json

  final case class JsString(value: String) extends Json

  final case class JsBoolean(value: Boolean) extends Json

  case object JsNull extends Json

  // Sequence

  sealed trait JsSequence extends Json

  final case class SeqCell(head: Json, tail: JsSequence) extends JsSequence

  case object SeqEnd extends JsSequence

  // Object

  sealed trait JsObject extends Json

  final case class ObjectCell(key: String, value: Json, tail: JsObject) extends JsObject

  case object ObjectEnd extends JsObject
}

import json._

//
//
// Tests

SeqCell(JsString("a string"), SeqCell(JsNumber(1.0), SeqCell(JsBoolean(true), SeqEnd))).print
// res0: String = ["a string", 1.0, true]

ObjectCell(
  "a",
  SeqCell(JsNumber(1.0), SeqCell(JsNumber(2.0), SeqCell(JsNumber(3.0), SeqEnd))),
  ObjectCell(
    "b",
    SeqCell(JsString("a"), SeqCell(JsString("b"), SeqCell(JsString("c"), SeqEnd))),
    ObjectCell(
      "c",
      ObjectCell("doh",
                 JsBoolean(true),
                 ObjectCell("ray", JsBoolean(false), ObjectCell("me", JsNumber(1.0), ObjectEnd))),
      ObjectEnd
    )
  )
).print
// res1: String = {"a": [1.0, 2.0, 3.0], "b": ["a", "b", "c"], "c": {"doh": true, "ray": false, "me": 1.0}}
