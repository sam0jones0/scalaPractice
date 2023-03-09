import scala.math.{pow, Pi}

sealed trait Color {
  def red: Short

  def green: Short

  def blue: Short

  def name: String

  def luminance: Float = {
    val rgbSeq: Seq[Float] = Seq(red / 255f, green / 255f, blue / 255f)
    (rgbSeq.min + rgbSeq.max) / 2 * 100
  }

  def isLight = luminance >= 50

  def isDark = !isLight

}

final case class customColor(red: Short, green: Short, blue: Short, colorName: String) extends Color {
  override val name = colorName
}

final case class Red() extends Color {
  override val red: Short   = 255
  override val green: Short = 0
  override val blue: Short  = 0
  override val name         = "red"
}

final case class Yellow() extends Color {
  override val red: Short   = 255
  override val green: Short = 255
  override val blue: Short  = 0
  override val name         = "yellow"
}

final case class Pink() extends Color {
  override val red: Short   = 255
  override val green: Short = 192
  override val blue: Short  = 203
  override val name         = "pink"
}

sealed trait Shape {
  def sides: Int

  def perimeter: Double

  def area: Double

  def color: Color
}

sealed trait Rectangular extends Shape {
  def height: Double

  def width: Double

  val sides                      = 4
  override val perimeter: Double = (height * 2) + (width * 2)
  override val area: Double      = height * width
}

final case class Square(size: Double, color: Color) extends Rectangular {
  val width  = size
  val height = size
}

final case class Rectangle(height: Double, width: Double, color: Color) extends Rectangular {}

final case class Circle(radius: Double, color: Color) extends Shape {
  override def sides: Int = 1

  val perimeter: Double = Pi * 2 * radius
  val area: Double      = Pi * pow(radius, 2)
}

case object Draw {
  def apply(shape: Shape) = shape match {
    case circle: Circle =>
      s"A ${circle.color.name} circle of area ${circle.area}, radius ${circle.radius} and perimeter ${circle.perimeter}"
    case rectangle: Rectangle =>
      s"A ${rectangle.color.name} rectangle of height ${rectangle.height}, width ${rectangle.width}, area ${rectangle.area}, and perimeter ${rectangle.perimeter}"
    case square: Square =>
      s"A ${square.color.name} square of size ${square.size}, area ${square.area}, and perimeter ${square.perimeter}"
    case rectangular: Rectangular =>
      s"A ${rectangular.color.name} rectangular shape of height ${rectangular.height}, width ${rectangular.width}, area ${rectangular.area}, and perimeter ${rectangular.perimeter}"
    case shape: Shape =>
      s"A ${shape.color.name} shape with ${shape.sides} sides, of perimeter ${shape.perimeter} and area ${shape.area}"
  }
}

//
// main

val c = Circle(radius = 10, color = Red())
//c.radius
//c.area
Draw(c)
