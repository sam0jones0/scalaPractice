// Using polymorphism and then using pattern matching implement a method called next
// which returns the next TrafficLight in the standard Red -> Green -> Yellow -> Red cycle.

// Pattern matching
//

sealed trait TrafficLight {
  def next(): TrafficLight =
    this match {
      case Red    => Green
      case Yellow => Red
      case Green  => Yellow
    }
}

case object Red extends TrafficLight

case object Green extends TrafficLight

case object Yellow extends TrafficLight

// Polymorphism
//

sealed trait TrafficLight {
  def next: TrafficLight
}

case object Red extends TrafficLight {
  override def next: TrafficLight = Green
}

case object Green extends TrafficLight {
  override def next: TrafficLight = Yellow
}

case object Yellow extends TrafficLight {
  override def next: TrafficLight = Red
}
