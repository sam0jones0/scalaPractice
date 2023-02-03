case class Person(firstName: String, lastName: String) {
  def name = s"$firstName $lastName"
}

object Person {
  def apply(name: String): Person = {
    val parts = name.split(" ")
    apply(parts(0), parts(1))
  }
}

val s1 = Person(firstName = "Sam", lastName = "Jones")
val s2 = Person(name = "Sam Jones")

object StormTrooper {
  def inspect(person: Person) = {
    person match {
      case Person("Han", "Solo")       => "Stop rebel scum!"
      case Person("Luke", "Skywalker") => "Stop rebel scum!"
      case Person(firstName, _)        => s"Move along, $firstName."
    }
  }
}

StormTrooper.inspect(Person("Han", "Solo"))
StormTrooper.inspect(Person("Luke Skywalker"))
StormTrooper.inspect(Person("Bing Bong"))
