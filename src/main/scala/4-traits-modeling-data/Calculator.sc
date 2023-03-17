import scala.Double.NaN
//
//

sealed trait Expression {
  def eval: Calculation = this match {
    case Number(value)            => value
    case Addition(left, right)    => left.eval + right.eval
    case Subtraction(left, right) => left.eval - right.eval
  }
}

sealed trait Calculation {
  def value = this match {
    case NaN    => ???
    case Double => ???
  }
}

final case class Successful(result: Int) extends Calculation

final case class Failure(result: Int) extends Calculation

final case class Number(value: Double) extends Expression

final case class Addition(left: Expression, right: Expression) extends Expression

final case class Subtraction(left: Expression, right: Expression) extends Expression

final case class Division(left: Expression, right: Expression) extends Expression

final case class SquareRoot(of: Expression) extends Expression

//

Subtraction(Addition(Number(5), Number(3)), Number(1)).eval
