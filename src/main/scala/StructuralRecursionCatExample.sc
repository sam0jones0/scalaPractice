sealed trait Food

case object Antelope extends Food

case object TigerFood extends Food

case object Licorice extends Food

final case class CatFood(food: String) extends Food

// The Sum Type Polymorphism Pattern

sealed trait Feline {
  def dinner: Food
}

final case class Lion() extends Feline {
  override def dinner: Food = Antelope
}

final case class Tiger() extends Feline {
  override def dinner: Food = TigerFood
}

final case class Panther() extends Feline {
  override def dinner: Food = Licorice
}

final case class Cat(favouriteFood: String) extends Feline {
  override def dinner: Food = CatFood(food = favouriteFood)
}

// The Sum Type Pattern Matching Pattern

sealed trait Feline {
  def dinner: Food =
    this match {
      case Lion()    => Antelope
      case Tiger()   => TigerFood
      case Panther() => Licorice
      case Cat(food) => CatFood(food)
    }
}

final case class Lion() extends Feline

final case class Tiger() extends Feline

final case class Panther() extends Feline

final case class Cat(favouriteFood: String) extends Feline
