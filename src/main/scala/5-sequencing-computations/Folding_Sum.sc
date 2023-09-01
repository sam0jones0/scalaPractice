sealed trait Sum[A, B] {
  def fold[C](ifLeft: A => C, ifRight: B => C): C =
    this match {
      case Left(value)  => ifLeft(value)
      case Right(value) => ifRight(value)
    }

}

final case class Left[A, B](value: A) extends Sum[A, B]

final case class Right[A, B](value: B) extends Sum[A, B]
