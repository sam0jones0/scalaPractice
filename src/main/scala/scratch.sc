val validChars = raw"[\w-]+".r

val tags = Map(
  "operation" -> "[object object]",
  "provider" -> "[object object]_nbcu",
  "proposition" -> "gb",
  "territory" -> "gb",
  "content-realm" -> "gb",
  "active-territory" -> "gb"
).filterNot { case (_, v) => v.isEmpty || !validChars.matches(v) }

println(tags)
