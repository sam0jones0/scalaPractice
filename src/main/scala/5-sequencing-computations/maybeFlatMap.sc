//

sealed trait Maybe[A] {
  def flatMap[B](fn: A => Maybe[B]): Maybe[B] = this match {
    case Full(value) => fn(value)
    case Empty()     => Empty[B]()
  }

  def map[B](fn: A => B): Maybe[B] =
    this match {
      case Full(value) => Full(fn(value))
      case Empty()     => Empty[B]()
    }
}

final case class Full[A](value: A) extends Maybe[A]
final case class Empty[A]() extends Maybe[A]

//

def mightFail1: Maybe[Int] =
  Full(1)

def mightFail2: Maybe[String] =
  Full("str")

def mightFail3: Maybe[Int] =
  Empty() // This one failed

val flapMappin: Maybe[String] = mightFail1.flatMap { x =>
  mightFail2.flatMap { y =>
    mightFail3.flatMap(z => Full(x + y + z)) // Empty()
  }
}

val forin: Maybe[String] = for {
  x <- mightFail1
  y <- mightFail2
  z <- mightFail3
} yield x + y + z // Empty()

val mappin: Maybe[Maybe[Maybe[Full[String]]]] = mightFail1.map { x =>
  mightFail2.map { y =>
    mightFail3.map { z =>
      Full(x + y + z) // Full(Full(Empty()))
    }
  }
}
