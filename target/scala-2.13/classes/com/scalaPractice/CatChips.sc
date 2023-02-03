case class Cat(colour: String, food: String)

object Cat {
  def apply(colour: String, food: String): Cat =
    new Cat(colour.toLowerCase, food.toLowerCase)
}

object ChipShop {
  def willServe(cat: Cat): Boolean =
    cat match {
      case Cat(_, "chips") => true
      case Cat(_, _)       => false
    }
}

val c = Cat("Black", "CHIPs")
c.food
c.colour

ChipShop.willServe(c)
