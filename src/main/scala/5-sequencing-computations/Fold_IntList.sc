sealed trait IntList[A] {

  def fold[B](end: B)(f: (A, B) => B): B =
    this match {
      case End()            => end
      case Pair(head, tail) => f(head, tail.fold(end)(f))
    }
}

case class End[A]() extends IntList[A] {
  override def toString: String = ""
}

final case class Pair[A](head: A, tail: IntList[A]) extends IntList[A]

val myList = Pair(1, Pair(2, Pair(3, End())))

myList.fold(0)((x, y) => x + y)
//myList.length()
//myList.product()
//myList.double()
