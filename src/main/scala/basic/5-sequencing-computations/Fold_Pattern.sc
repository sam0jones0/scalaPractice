//
// Fold Pattern
//
// For an algebraic datatype A, fold converts it to a generic type B. Fold is a
// structural recursion with:
//
// - one function parameter for each case in A;
// - each function takes as parameters the fields for its associated class;
// - if A is recursive, any function parameters that refer to a recursive field take a

//   parameter of type B.
//
// The right-hand side of pattern matching cases, or the polymorphic methods as
// appropriate, consists of calls to the appropriate function.

sealed trait LinkedList[A] {
  def fold[B](end: B, pair: (A, B) => B): B =
    this match {
      case End()            => end
      case Pair(head, tail) => pair(head, tail.fold(end, pair))
    }
//
//  val next = () =>
//    this match {
//      case End()         => End()
//      case Pair(_, tail) => tail
//    }
}

final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

final case class End[A]() extends LinkedList[A]

val myList = Pair(1, Pair(2, Pair(3, End())))
//
//myList.next()
