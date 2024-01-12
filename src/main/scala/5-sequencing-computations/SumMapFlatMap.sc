sealed trait Sum[A, B] {
  def fold[C](left: A => C, right: B => C): C =
    this match {
      case Failure(a) => left(a)
      case Success(b) => right(b)
    }

  def map[C](f: B => C): Sum[A, C] =
    this match {
      case Failure(value) => Failure(value)
      case Success(value) => Success(f(value))
    }

  def flatMap[C](f: B => Sum[A, C]): Sum[A, C] =
    this match {
      case Failure(value) => Failure(value)
      case Success(value) => f(value)
    }
}

final case class Failure[A, B](value: A) extends Sum[A, B]
final case class Success[A, B](value: B) extends Sum[A, B]

val num: Failure[String, Int]  = Failure("error")
val num2: Success[String, Int] = Success(1)

val mappin: Sum[String, Success[Nothing, Byte]] = num2.map(_.toLong).map(a => Success(a.toByte))
val mappin2: Sum[String, Byte]                  = num2.map(_.toLong).map(a => a.toByte)
val flatMappin: Sum[String, Byte]               = num2.flatMap(a => Success(a.toLong)).flatMap(b => Success(b.toByte))
