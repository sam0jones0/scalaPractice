import scala.math.{Pi, pow}

sealed trait Shape {
  def sides: Int

  def perimeter: Double

  def area: Double
}

sealed trait Rectangular extends Shape {
  def height: Double

  def width: Double

  val sides = 4
  override val perimeter: Double = (height * 2) + (width * 2)
  override val area: Double = height * width
}

final case class Square(size: Double) extends Rectangular {
  val width = size
  val height = size
}

final case class Rectangle(height: Double, width: Double) extends Rectangular {}

final case class Circle(radius: Double) extends Shape {
  override def sides: Int = 1

  val perimeter: Double = Pi * 2 * radius
  val area: Double = Pi * pow(radius, 2)
}

case object Draw {
  def apply(shape: Shape) = shape match {
    case circle: Circle =>
      s"A circle of area ${circle.area}, radius ${circle.radius} and perimeter ${circle.perimeter}"
    case rectangle: Rectangle =>
      s"A rectangle of height ${rectangle.height}, width ${rectangle.width}, area ${rectangle.area}, and perimeter ${rectangle.perimeter}"
    case square: Square =>
      s"A square of size ${square.size}, area ${square.area}, and perimeter ${square.perimeter}"
    case rectangular: Rectangular =>
      s"A rectangular shape of height ${rectangular.height}, width ${rectangular.width}, area ${rectangular.area}, and perimeter ${rectangular.perimeter}"
    case shape: Shape =>
      s"A shape with ${shape.sides} sides, of perimeter ${shape.perimeter} and area ${shape.area}"
  }
}

//
// main

val c = Circle(radius = 10)
//c.radius
//c.area
Draw(c)
