package day06

import solution

fun main() = solution { lines ->
  val allNumbers = mutableListOf<List<Long>>()
  var currentNumbers = mutableListOf<Long>()
  for (j in lines[0].indices) {
    val builder = StringBuilder()
    for (i in 0..<lines.size - 1) {
      if (lines[i][j] != ' ') builder.append(lines[i][j])
    }
    if (builder.isEmpty()) {
      allNumbers.add(currentNumbers)
      currentNumbers = mutableListOf()
    } else {
      currentNumbers.add(builder.toString().toLong())
    }
  }
  allNumbers.add(currentNumbers)

  val operations = lines.last().split(" ").filter(String::isNotEmpty)

  var answer = 0L
  for ((numbers, operation) in allNumbers.zip(operations)) {
    val isMultiply = operation == "*"
    var result = if (isMultiply) 1L else 0L
    for (number in numbers) {
      if (isMultiply) result *= number else result += number
    }
    answer += result
  }

  println(answer)
}