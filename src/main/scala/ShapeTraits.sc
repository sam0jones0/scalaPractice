import scala.math.{Pi, pow}

trait Shape {
  def sides: Int

  def perimeter: Double

  def area: Double
}

trait Rectangular extends Shape {
  def height: Double

  def width: Double

  val sides = 4
  override val perimeter: Double = (height * 2) + (width * 2)
  override val area: Double = height * width
}

case class Square(size: Double) extends Rectangular {
  val width = size
  val height = size
}

case class Rectangle(height: Double, width: Double) extends Rectangular {}

case class Circle(radius: Double) extends Shape {
  override def sides: Int = 1

  val perimeter: Double = Pi * 2 * radius
  val area: Double = Pi * pow(radius, 2)
}

val c = Circle(10)
c.radius
c.area
