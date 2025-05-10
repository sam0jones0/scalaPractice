// Traffic light is a red or yellow or green [traffic light]

sealed trait TrafficLight

case object Red extends TrafficLight

case object Yellow extends TrafficLight

case object Green extends TrafficLight

//
// Calculation has a result, which is a success or failure

sealed trait CalcResult

sealed trait Calculation {
  def result: CalcResult
}

final case class Success(result: Int) extends CalcResult

final case class Failure(message: String) extends CalcResult

// But actually, a calculation is-a success or failure. Calculation cannot have a failure.
// So really the proper model is:

sealed trait Calculation

final case class Success(result: Int) extends Calculation

final case class Failure(message: String) extends Calculation

//
// Water has a size, a source and a carbonated bool.

sealed trait WaterSource

final case class Well() extends WaterSource

final case class Spring() extends WaterSource

final case class Tap() extends WaterSource

case class Water(size: Int, source: WaterSource, carbonated: Boolean)
