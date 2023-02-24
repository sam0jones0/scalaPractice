//
// General skeleton for Recursive Algebraic Data Types Pattern

sealed trait RecursiveExample

final case class RecursiveCase(recursion: RecursiveExample) extends RecursiveExample

case object BaseCase extends RecursiveExample

//
//

sealed trait IntList {
  def sum(): Int = this match {
    case End              => 0
    case Pair(head, tail) => head + tail.sum
  }

  def toString: String
}

case object End extends IntList {
  override def toString: String = ""
}

final case class Pair(head: Int, tail: IntList) extends IntList {
  override def toString: String = s"${head.toString}, ${tail.toString}"
}

val myList = Pair(1, Pair(2, Pair(3, End)))

val example = Pair(1, Pair(2, Pair(3, End)))

assert(example.sum == 6)
assert(example.tail.sum == 5)
assert(End.sum == 0)
