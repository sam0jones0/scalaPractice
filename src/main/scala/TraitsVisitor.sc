import java.util.Date

sealed trait Visitor {
  def id: String

  def createdAt: Date

  def age: Long = new Date().getTime - createdAt.getTime
}

final case class Anonymous(id: String, createdAt: Date = new Date())
    extends Visitor

final case class User(id: String, email: String, createdAt: Date = new Date())
    extends Visitor

def missingCase(v: Visitor): Unit = {
  v match {
    case Anonymous(k, _) if k.nonEmpty => "User with nonempty ID"
    case Anonymous(k, _) if k.isEmpty  => "User with empty ID"
    case Anonymous(_, _)               => "Got some other unknown user"
    case User(_, _, _)                 => "Got some other user"
  }
}

//
// main

def older(v1: Visitor, v2: Visitor): Boolean = v1.createdAt.before(v2.createdAt)

val anonUser = Anonymous("1")
val knownUser = User("2", "test@example.com")

older(anonUser, knownUser)
