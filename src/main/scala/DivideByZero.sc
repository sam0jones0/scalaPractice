sealed trait DivisionResult

case class Finite(result: Int) extends DivisionResult

case object Infinite extends DivisionResult

object divide {
  def apply(intA: Int, intB: Int): DivisionResult =
    if (intB == 0) Infinite
    else Finite(intA / intB)
}

divide(5, 2) match {
  case Finite(value) => s"Division resulted in $value"
  case Infinite      => "Divide by zero"
}
