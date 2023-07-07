// Binary Tree Fold Pattern

sealed trait BinaryTree[A] {
  def fold[B](node: (B, B) => B, leaf: A => B): B
}

final case class Node[A](left: BinaryTree[A], right: BinaryTree[A]) extends BinaryTree[A] {
  override def fold[B](node: (B, B) => B, leaf: A => B): B =
    node(left.fold(node, leaf), right.fold(node, leaf))
}

final case class Leaf[A](element: A) extends BinaryTree[A] {
  override def fold[B](node: (B, B) => B, leaf: A => B): B = leaf(element)
}

val intTree = Node(left = Node(left = Leaf(0), right = Leaf(1)), right = Leaf(2))

val strTree: BinaryTree[String] =
  Node(Node(Leaf("To"), Leaf("iterate")),
       Node(Node(Leaf("is"), Leaf("human,")), Node(Leaf("to"), Node(Leaf("recurse"), Leaf("divine")))))

intTree.fold[String]((x, y) => s"$x $y", _.toString)

strTree.fold[String]((x, y) => s"$x $y", _.toString)
