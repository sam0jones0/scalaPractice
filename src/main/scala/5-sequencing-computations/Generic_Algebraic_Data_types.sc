import scala.annotation.tailrec
//

sealed trait Result[A]

final case class Success[A](result: A) extends Result[A]

final case class Failure[A](reason: String) extends Result[A]

//
// Invariant Generic Sum Type Pattern: Linked List (with toString)

sealed trait LinkedList[A] {

  @tailrec
  final def apply(position: Int): A =
    this match {
      case End()                      => throw new Exception(s"No node at position: $position")
      case LinkedListNode(data, next) => if (position == 0) data else next(position - 1)
    }

  def toString(linkedList: LinkedList[A]): String =
    linkedList match {
      case LinkedListNode(data, End()) => s"${data.toString}"
      case LinkedListNode(data, next)  => s"${data.toString}, ${toString(next)}"
      case End()                       => ""
    }

  def print = s"[${toString(this)}]"

  def length: Int =
    this match {
      case End()                   => 0
      case LinkedListNode(_, next) => 1 + next.length
    }

  @tailrec
  final def contains(value: A): Boolean =
    this match {
      case End()                      => false
      case LinkedListNode(data, next) => value == data || next.contains(value)
    }

}

final case class End[A]() extends LinkedList[A]

final case class LinkedListNode[A](data: A, next: LinkedList[A]) extends LinkedList[A]

val l1 = LinkedListNode(1, LinkedListNode(2, LinkedListNode(3, End())))
l1.print // val res0: String = [1, 2, 3]

val l2 = LinkedListNode('A', LinkedListNode('B', LinkedListNode('C', End())))
l2.print // val res1: String = [A, B, C]

LinkedListNode(1, End()).print   // val res2: String = [1]
LinkedListNode("a", End()).print // val res3: String = [a]

val example = LinkedListNode(1, LinkedListNode(2, LinkedListNode(3, End())))
assert(example.length == 3)
assert(example.next.length == 2)
assert(End().length == 0)

assert(example.contains(3))
assert(!example.contains(4))
assert(!End().contains(0))

// This should not compile
// example.contains("not an Int")

val example = LinkedListNode(1, LinkedListNode(2, LinkedListNode(3, End())))
assert(example(0) == 1)
assert(example(1) == 2)
assert(example(2) == 3)
assert(try {
  example(3)
  false
} catch {
  case e: Exception => true
})
