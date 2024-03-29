import scala.annotation.tailrec
//
// General skeleton for Recursive Algebraic Data Types Pattern

sealed trait RecursiveExample

final case class RecursiveCase(recursion: RecursiveExample) extends RecursiveExample

case object BaseCase extends RecursiveExample

//
//

sealed trait IntList {
  //  @tailrec // error
  final def sumNonTail(): Int = this match {
    case End              => 0
    case Pair(head, tail) => head + tail.sumNonTail()
  }

  @tailrec
  final def sumTail(total: Int = 0): Int = this match {
    case End              ”=> total
    case Pair(head, tail) => tail.sumTail(total + head)
  }

  def toString: String

  def length: Int =
    this match {
      case End           => 0
      case Pair(_, tail) => 1 + tail.length
    }

  def product: Int =
    this match {
      case End              => 1
      case Pair(head, tail) => head * tail.product
    }

  def double: IntList =
    this match {
      case End              => End
      case Pair(head, tail) => Pair(head * 2, tail.double)
    }

}

case object End extends IntList {
  override def toString: String = ""
}

final case class Pair(head: Int, tail: IntList) extends IntList {
  override def toString: String = s"${head.toString}, ${tail.toString}"
}

//val myList = Pair(1, Pair(2, Pair(3, End)))
//
//val example = Pair(1, Pair(2, Pair(3, End)))
