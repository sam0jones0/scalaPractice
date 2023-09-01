//

sealed trait LinkedList[A] {
  def map[B](fn: A => B): LinkedList[B] =
    this match {
      case Pair(head, tail) => Pair(fn(head), tail.map(fn))
      case End()            => End()
    }
}

final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

final case class End[A]() extends LinkedList[A]

val intList        = Pair(1, Pair(2, Pair(3, End())))
val strListTimes10 = intList.map(_ * 10).map(_.toString)
strListTimes10.map(x => println(x))
