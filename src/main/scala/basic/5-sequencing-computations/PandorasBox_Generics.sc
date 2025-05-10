//

final case class Box[A, B](value: A, valueB: B)

Box(1, "a")

Box(1.0, 'a')

Box(0f, 0)

Box('a', null)

Box("a", Seq(1))

Box(Seq(1, 2, 3, 3.0), Option(1))

def print[A](in: A) = in

print(23)
