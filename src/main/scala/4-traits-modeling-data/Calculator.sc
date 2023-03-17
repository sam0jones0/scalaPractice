import scala.Double.NaN
//
//

sealed trait Calculation

final case class Success(result: Double) extends Calculation

final case class Failure(reason: String) extends Calculation

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

    case Division(left, right) =>
      left.eval match {
        case Failure(reason) => Failure(reason)
        case Success(leftValue) =>
          right.eval match {
            case Failure(reason) => Failure(reason)
            case Success(rightValue) =>
              rightValue match {
                case 0 => Failure("Division by zero")
                case _ => Success(leftValue / rightValue)
              }
          }
      }

    case SquareRoot(of) =>
      of.eval match {
        case Failure(reason) => Failure(reason)
        case Success(value) =>
          value match {
            case value if value < 0 => Failure("Square root of negative number")
            case _                  => Success(Math.sqrt(value))
          }
      }

    case Number(value) => Success(value)
  }
}

//

final case class Number(value: Double) extends Expression

final case class Addition(left: Expression, right: Expression) extends Expression

final case class Subtraction(left: Expression, right: Expression) extends Expression

final case class Division(left: Expression, right: Expression) extends Expression

final case class SquareRoot(of: Expression) extends Expression

//
//
//Subtraction(Addition(Number(5), Number(3)), Number(1)).eval

assert(
  Addition(SquareRoot(Number(-1.0)), Number(2.0)).eval ==
    Failure("Square root of negative number"))
assert(Addition(SquareRoot(Number(4.0)), Number(2.0)).eval == Success(4.0))
assert(Division(Number(4), Number(0)).eval == Failure("Division by zero"))
