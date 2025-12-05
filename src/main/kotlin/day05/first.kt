package day05

import solution

fun main() = solution { lines ->
  val ranges = lines
    .takeWhile { it.isNotBlank() }
    .map { line -> line.split("-").let { it[0].toLong()..it[1].toLong() } }

  lines
    .dropWhile { it.isNotBlank() }
    .drop(1)
    .map { it.toLong() }
    .count { id -> ranges.any { range -> id in range } }
    .let { println(it) }
}