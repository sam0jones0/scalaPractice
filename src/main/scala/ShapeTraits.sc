import scala.math.{Pi, pow}

trait Shape {
  def sides: Int

  def perimeter: Double

  def area: Double
}

case class Square(perimeter: Double) extends Shape {
  override val sides: Int = 4
  val area: Double = pow(perimeter / 4, 2)
}

case class Rectangle(height: Double, width: Double) extends Shape {
  override val sides: Int = 4
  val perimeter: Double = (height * 2) + (width * 2)
  val area: Double = height * width
}

case class Circle(radius: Double) extends Shape {
  override def sides: Int = 1
  val perimeter: Double = Pi * 2 * radius
  val area: Double = Pi * pow(radius, 2)
}

val c = Circle(10)
c.radius
c.area
