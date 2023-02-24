// Create a Calculator object. On Calculator define methods + and - that accept a
// Calculation and an Int, and return a new Calculation.

sealed trait Calculation

final case class Success(result: Int) extends Calculation

final case class Failure(reason: String) extends Calculation

case object Calculator {
  def +(calculation: Calculation, num: Int) =
    calculation match {
      case Success(result) => Success(result + num)
      case Failure(reason) => Failure(reason)
    }

  def -(calculation: Calculation, num: Int) =
    calculation match {
      case Success(result) => Success(result - num)
      case Failure(reason) => Failure(reason)
    }

  //  def /(calculation: Calculation, num: Int) =
  //    calculation match {
  //      case Failure(reason)                   => Failure(reason)
  //      case Success(result) if !num.equals(0) => Success(result / num)
  //      case _                                 => Failure("Division by zero")
  //    }

  def /(calculation: Calculation, num: Int) =
    calculation match {
      case Success(result) =>
        num match {
          case 0 => Failure("Division by zero")
          case _ => Success(result / num)
        }
      case Failure(reason) => Failure(reason)
    }
}

// Tests
assert(Calculator.+(Success(1), 1) == Success(2))
assert(Calculator.-(Success(1), 1) == Success(0))
assert(Calculator.+(Failure("Badness"), 1) == Failure("Badness"))

assert(Calculator./(Success(4), 2) == Success(2))
assert(Calculator./(Success(4), 0) == Failure("Division by zero"))
assert(Calculator./(Failure("Badness"), 0) == Failure("Badness"))
