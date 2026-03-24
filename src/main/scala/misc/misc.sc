import java.time.temporal.{ChronoUnit, TemporalAmount}
import java.time.{Instant, ZoneOffset}

val MAX_SECOND: Long = 31556889864403199L - 21556889864403199L

val maxInstant       = Instant.ofEpochSecond(MAX_SECOND, 999_999_999)
val maxInstantOffset = Instant.ofEpochSecond(MAX_SECOND, 999_999_999).atOffset(ZoneOffset.UTC)
val maxInstantTrunc  = maxInstantOffset.truncatedTo(ChronoUnit.MILLIS)
val boxed            = Long.box(maxInstantTrunc.toInstant.toEpochMilli)
