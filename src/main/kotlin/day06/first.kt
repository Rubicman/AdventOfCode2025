package day06

import solution

fun main() = solution { lines ->
  val numbers = lines
    .dropLast(1)
    .map { line -> line
      .split(" ")
      .filter(String::isNotEmpty)
      .map { it.toLong() }
    }
  val n = numbers.size
  val m = numbers[0].size

  val operations = lines.last().split(" ").filter(String::isNotEmpty)

  var answer = 0L
  for (j in 0..<m) {
    val isMultiply = operations[j] == "*"
    var result = if (isMultiply) 1L else 0L
    for (i in 0..<n) {
      if (isMultiply) result *= numbers[i][j] else result += numbers[i][j]
    }
    answer += result
  }

  println(answer)
}