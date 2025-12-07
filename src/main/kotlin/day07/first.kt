package day07

import solution

fun main() = solution { lines ->
  var prevLine = mutableSetOf<Int>()
  var nextLine = mutableSetOf<Int>()
  var answer = 0

  for (j in lines[0].indices) {
    if (lines[0][j] == 'S') prevLine.add(j)
  }

  for (i in 1..<lines.size) {
    for (j in prevLine) {
      if (lines[i][j] == '^') {
        nextLine.add(j - 1)
        nextLine.add(j + 1)
        answer++
      } else {
        nextLine.add(j)
      }
    }

    prevLine = nextLine
    nextLine = mutableSetOf()
  }

  println(answer)
}