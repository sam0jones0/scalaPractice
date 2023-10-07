import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

//

case class SafeValue[+T](private val internalValue: T) { // "contructor" = pure, or unit
  def get: T = synchronized(
    // does something interesting
    internalValue
  )

  def flatMap[S](transformer: T => SafeValue[S]): SafeValue[S] = synchronized( // bind, or flatMap
    transformer(internalValue))
}

// "external" API
def gimmieSafeValue[T](value: T): SafeValue[T] = SafeValue(value)

val safeString: SafeValue[String] = gimmieSafeValue("Scala is awesome")
// extract
val string = safeString.get
// transform
val upperString = string.toUpperCase()
// wrap
val upperSafeString = SafeValue(upperString)
// ETW

// compressed:
val upperSafeString2 = safeString.flatMap(s => SafeValue(s.toUpperCase())).flatMap(a => SafeValue(a))

// Examples

// Example 1: census
case class Person(firstName: String, lastName: String) {
  assert(firstName != null && lastName != null)
}

// census API
def getPerson(firstName: String, lastName: String): Person =
  if (firstName != null) {
    if (lastName != null) {
      Person(firstName, lastName)
    } else {
      null
    }
  } else {
    null
  }

def getPersonBetter(firstName: String, lastName: String): Option[Person] =
  Option(firstName).flatMap(fName => Option(lastName).flatMap(lName => Option(Person(fName, lName))))

def getPersonFor(firstName: String, lastName: String): Option[Person] = for {
  fName <- Option(firstName)
  lName <- Option(lastName)
} yield Person(fName, lName)

// Example 2: asynchronous fetches

case class User(id: String)
case class Product(sku: String, price: Double)

def getUser(url: String): Future[User] = Future {
  User("daniel")
}

def getLastOrder(userId: String): Future[Product] = Future {
  Product("123-456", 99.99)
}

val danielsUrl = "store.com/user/daniel"

// ETW

getUser(danielsUrl).onComplete { case Success(user) =>
  getLastOrder(user.id).onComplete { case Success(product) =>
    val vatPrice = 1.2 * product.price
    println(vatPrice)
  // Pass it on - sent Daniel an email etc
  }
}

val vatInclPrice: Future[Double] = getUser(danielsUrl).flatMap(user => getLastOrder(user.id)).map(_.price * 1.2)

val vatInclPriceFor: Future[Double] = for {
  user    <- getUser(danielsUrl)
  product <- getLastOrder(user.id)
} yield product.price * 1.2

println("end")
