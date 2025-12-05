package day05

import solution
import java.math.BigDecimal.ZERO

fun main() = solution { lines ->
  var answer = ZERO
  lines
    .takeWhile { it.isNotBlank() }
    .map { line -> line.split("-").let { it[0].toLong()..it[1].toLong() } }
    .sortedBy { it.first }
    .reduce { acc, range ->
      if (acc.last < range.first) {
        answer += acc.size().toBigDecimal()
        range
      } else acc.first..maxOf(acc.last, range.last)
    }.let { answer += it.size().toBigDecimal() }

  println(answer)
}

private fun LongRange.size() = last - first + 1