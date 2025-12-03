package day02

import solution

fun main() = solution { lines ->
  lines
    .first()
    .split(",")
    .flatMap { range ->
      range
        .split("-")
        .map(String::toLong)
        .let { it[0]..it[1] }
    }
    .map { it.toString() }
    .filter { number ->
      (1..number.length / 2)
        .any { n ->
          number.take(n)
            .repeat(number.length / n) == number
        }
    }
    .sumOf { it.toLong() }
    .let { println(it) }
}