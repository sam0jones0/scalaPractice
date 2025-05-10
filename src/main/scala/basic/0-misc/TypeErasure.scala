import scala.compiletime.{erasedValue, summonInline}

case class Thing[T](value: T)

case object blah extends App {
  inline def processThing[T](thing: Thing[T]): String =
    inline erasedValue[T] match
      case _: Seq[Int]    => "Thing of Seq[Int]"
      case _: Seq[String] => "Thing of Seq[String]"
      case _: Int         => "Thing of Int"
      case _              => "Thing of other"

  println(processThing(Thing(1)))
  println(processThing(Thing("hello")))
  println(processThing(Thing(Seq(1))))
  println(processThing(Thing(Seq("hello"))))

}
