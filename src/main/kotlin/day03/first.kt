package day03

import solution

fun main() = solution { lines ->
  lines.sumOf { line ->
    var answer = "00"
    var maxDigit = line.last()
    for (i in line.length - 2 downTo 0) {
      answer = maxOf(answer, "${line[i]}$maxDigit")
      maxDigit = maxOf(maxDigit, line[i])
    }
    answer.toInt()
  }
    .let { println(it) }
}