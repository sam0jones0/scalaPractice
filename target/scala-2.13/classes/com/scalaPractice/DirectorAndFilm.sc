case class Director(
    firstName: String,
    lastName: String,
    yearOfBirth: Int
) {

  def name: String =
    s"$firstName $lastName"
}

object Director {
  def older(director1: Director, director2: Director): Director =
    if (director1.yearOfBirth < director2.yearOfBirth) director1 else director2
}

case class Film(
    name: String,
    yearOfRelease: Int,
    imdbRating: Double,
    director: Director
) {

  def directorsAge =
    yearOfRelease - director.yearOfBirth

  def isDirectedBy(director: Director) =
    this.director == director

}

object Film {
  def newer(film1: Film, film2: Film): Film =
    if (film1.yearOfRelease < film2.yearOfRelease) film1 else film2

  def highestRating(film1: Film, film2: Film): Double = {
    val rating1 = film1.imdbRating
    val rating2 = film2.imdbRating
    if (rating1 > rating2) rating1 else rating2
  }

  def oldestDirectorAtTheTime(filmA: Film, filmB: Film): Director = {
    if (filmA.directorsAge > filmB.directorsAge) filmA.director
    else filmB.director
  }
}

val d1 = Director(firstName = "Sam", lastName = "Jones", yearOfBirth = 1991)
val d2 = Director(firstName = "Ham", lastName = "Bones", yearOfBirth = 2009)
val f1 =
  Film(name = "Poo", yearOfRelease = 2000, imdbRating = 8.8, director = d1)
val f2 =
  Film(name = "Foo", yearOfRelease = 2010, imdbRating = 8.4, director = d2)

Film.highestRating(f1, f2)
Film.oldestDirectorAtTheTime(f1, f2)
