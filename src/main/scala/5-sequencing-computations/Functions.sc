import scala.annotation.tailrec

sealed trait IntList {

  def fold[A](end: A, f: (Int, A) => A): A =
    this match {
      case End              => end
      case Pair(head, tail) => f(head, tail.fold(end, f))
    }

  val sum     = () => this.fold[Int](0, (x, y: Int) => x + y)
  val length  = () => this.fold(0, (_, y: Int) => 1 + y)
  val product = () => this.fold(1, (x, y: Int) => x * y)
  val double  = () => this.fold(End, (x, y) => Pair(x * 2, y))

  def toString: String

  //  def sum(total: Int = 0): Int = this match {
  //    case End              => total
  //    case Pair(head, tail) => tail.sum(total + head)
  //  }

  //  def length: Int =
  //    this match {
  //      case End           => 0
  //      case Pair(_, tail) => 1 + tail.length
  //    }

  //  def product: Int =
  //    this match {
  //      case End              => 1
  //      case Pair(head, tail) => head * tail.product
  //    }

  //  def double: IntList =
  //    this match {
  //      case End              => End
  //      case Pair(head, tail) => Pair(head * 2, tail.double)
  //    }

}

case object End extends IntList {
  override def toString: String = ""
}

final case class Pair(head: Int, tail: IntList) extends IntList {
  override def toString: String = s"${head.toString}, ${tail.toString}"
}

val myList = Pair(1, Pair(2, Pair(3, End)))

myList.sum()
myList.length()
myList.product()
myList.double()
