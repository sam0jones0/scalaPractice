sealed trait Maybe[A] {
  def fold[B](ifEmpty: B, f: A => B): B =
    this match {
      case Full(a) => f(a)
      case Empty() => ifEmpty
    }
}

final case class Full[A](value: A) extends Maybe[A] {
  //  def fold[B](ifEmpty: B, f: (A => B)): B = f(a)
}

final case class Empty[A]() extends Maybe[A] {
  //  def fold[B](ifEmpty: B, f: (A => B)): B = ifEmpty
}
