// A binary tree of integers can be defined as follows:
//
//A Tree is a Node with a left and right Tree or a Leaf with an element of type Int.
//
//Implement this algebraic data type.
//
// Implement sum and double on Tree using polymorphism and pattern matching.

sealed trait IntTree {
  val element: Int

  final def sum(): Int = this match {
    case Node(element, left, right) => element + left.sum + right.sum
    case Leaf(element) => element
  }

  final def double: IntTree = this match {
    case Node(element, left, right) => Node(element * 2, left.double, right.double)
    case Leaf(element) => Leaf(element * 2)
  }
}

final case class Node(element: Int, left: IntTree, right: IntTree) extends IntTree

final case class Leaf(element: Int) extends IntTree

//val a = Node

val a = Node(1, Leaf(2), Node(3, Leaf(4), Leaf(5)))
a.sum()
a.double.sum()
