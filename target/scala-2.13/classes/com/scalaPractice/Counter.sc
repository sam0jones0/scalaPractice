case class Counter(val count: Int = 0) {
  def dec = copy(count = count - 1)

  def inc = copy(count = count + 1)

}

val c = Counter(5)
val d = Counter()

d.inc.dec.count
c.inc.inc.count
