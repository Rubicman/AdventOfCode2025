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
    .filter { it.take(it.length / 2) == it.substring(it.length / 2) }
    .sumOf { it.toLong() }
    .let { println(it) }
}