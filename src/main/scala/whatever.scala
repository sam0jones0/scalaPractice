package com.scalaPractice

object whatever {

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
      case End              => total
      case Pair(head, tail) => tail.sumTail(total + head)
    }

    def toString: String
  }

  case object End extends IntList {
    override def toString: String = ""
  }

  final case class Pair(head: Int, tail: IntList) extends IntList {
    override def toString: String = s"${head.toString}, ${tail.toString}"
  }

  def main(args: Array[String]): Unit = {

    val myList = Pair(1, Pair(2, Pair(3, End)))

    val example = Pair(1, Pair(2, Pair(3, End)))

    // assert(example.sumNonTail == 6)
    // assert(example.tail.sumNonTail == 5)
    // assert(End.sumNonTail == 0)

    assert(example.sumTail() == 6)
    assert(example.tail.sumTail() == 5)
    assert(End.sumTail() == 0)
  }
}
