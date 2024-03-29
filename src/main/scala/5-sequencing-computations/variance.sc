//

sealed trait Maybe[+A]

final case class Full[A](value: A) extends Maybe[A]

case object Empty extends Maybe[Nothing]

val possible: Maybe[Int] = Empty

// Covariant [+T] positions allow substitution with more specific types (subtypes).
// Contravariant [-T] positions allow substitution with more general types (supertypes).
