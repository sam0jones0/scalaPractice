trait Feline {
  def colour: String

  def sound: String
}

trait BigCat extends Feline {
  override val sound: String = "roar"
}

case class Cat(colour: String, food: String) extends Feline {
  val sound = "meow"
}

case class Lion(colour: String, maneSize: Int) extends BigCat

case class Tiger(colour: String) extends BigCat
