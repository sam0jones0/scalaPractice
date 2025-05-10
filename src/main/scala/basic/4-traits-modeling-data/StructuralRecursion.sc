case class F()

// The Product Type Polymorphism Pattern
// A has a b (with type B) and a c (with type C), and we want to write a method f returning an F
//

case class A(b: B, c: C) {
  def f: F = ???
}

// The Sum Type Polymorphism Pattern
// A is a B or C, and we want to write a method f returning an F
//

sealed trait A {
  def f: F
}

final case class B() extends A {
  def f: F =
    ???
}

final case class C() extends A {
  def f: F =
    ???
}

// The Product Type Pattern Matching Pattern
// A has a b (with type B) and a c (with type C), and we want to write a method f that accepts an A and returns an F
//

def f(a: A): F =
  a match {
    case A(b: B, c: C) => ???
  }

// The Sum Type Pattern Matching Pattern
// A is a B or C, and we want to write a method f that accepts an A and returns an F

def f(a: A): F =
  a match {
    case B() => ???
    case C() => ???
  }
