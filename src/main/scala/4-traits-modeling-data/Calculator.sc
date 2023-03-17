import scala.Double.NaN
//
//

sealed trait Expression {
  def eval: Calculation = this match {

    case Addition(left, right) =>
      left.eval match {
        case Failure(reason) => Failure(reason)
        case Success(leftValue) =>
          right.eval match {
            case Failure(reason)     => Failure(reason)
            case Success(rightValue) => Success(leftValue + rightValue)
          }
      }

    case Subtraction(left, right) =>
      left.eval match {
        case Failure(reason) => Failure(reason)
        case Success(leftValue) =>
          right.eval match {
            case Failure(reason)     => Failure(reason)
            case Success(rightValue) => Success(leftValue - rightValue)
          }
      }

    case Division(left, right) => ???
    case SquareRoot(of)        => ???

    case Number(value) => Success(value)
  }
}

sealed trait Calculation

final case class Success(result: Double) extends Calculation

final case class Failure(reason: String) extends Calculation

//

final case class Number(value: Double) extends Expression

final case class Addition(left: Expression, right: Expression) extends Expression

final case class Subtraction(left: Expression, right: Expression) extends Expression

final case class Division(left: Expression, right: Expression) extends Expression

final case class SquareRoot(of: Expression) extends Expression

//

Subtraction(Addition(Number(5), Number(3)), Number(1)).eval
