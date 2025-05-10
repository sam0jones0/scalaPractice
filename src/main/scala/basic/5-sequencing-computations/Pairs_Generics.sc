// Generic Product type

case class Pair[A, B](one: A, two: B)

val pair = Pair[String, Int]("hi", 2)

pair.one
pair.two

// Generic Sum type

trait Sum[A, B]

case class Left[A, B](value: A) extends Sum[A, B]

case class Right[A, B](value: B) extends Sum[A, B]

Left[Int, String](1).value

Right[Int, String]("foo").value

val sum: Sum[Int, String] = Right("foo")

sum match {
  case Left(x)  => x.toString
  case Right(x) => x
}

// Generic Optional values

trait Maybe[A]

final case class Full[A](value: A) extends Maybe[A]

final case class Empty[A]() extends Maybe[A]

val perhaps: Maybe[Int] = Empty[Int]

val perhaps: Maybe[Int] = Full(1)
