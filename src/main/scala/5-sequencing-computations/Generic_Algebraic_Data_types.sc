//

sealed trait Result[A]

final case class Success[A](result: A) extends Result[A]

final case class Failure[A](reason: String) extends Result[A]

//
// Invariant Generic Sum Type Pattern: Linked List (with toString)

sealed trait LinkedList[A] {
  def toString(linkedList: LinkedList[A]): String =
    linkedList match {
      case LinkedListNode(value, End()) => s"${value.toString}"
      case LinkedListNode(value, next)  => s"${value.toString}, ${toString(next)}"
      case End()                        => ""
    }

  def print = s"[${toString(this)}]"

}

final case class End[A]() extends LinkedList[A]

final case class LinkedListNode[A](value: A, next: LinkedList[A]) extends LinkedList[A]

val l1 = LinkedListNode(1, LinkedListNode(2, LinkedListNode(3, End())))
l1.print // val res0: String = [1, 2, 3]

val l2 = LinkedListNode('A', LinkedListNode('B', LinkedListNode('C', End())))
l2.print // val res1: String = [A, B, C]

LinkedListNode(1, End()).print   // val res2: String = [1]
LinkedListNode("a", End()).print // val res3: String = [a]
