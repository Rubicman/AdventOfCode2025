package day07

import solution

fun main() = solution { lines ->
  var prevLine = mutableMapOf<Int, Long>().withDefault { 0 }
  var nextLine = mutableMapOf<Int, Long>().withDefault { 0 }

  for (j in lines[0].indices) {
    if (lines[0][j] == 'S') prevLine[j] = 1
  }

  for (i in 1..<lines.size) {
    for ((j, count) in prevLine) {

      if (lines[i][j] == '^') {
        nextLine[j - 1] = nextLine.getValue(j - 1) + count
        nextLine[j + 1] = nextLine.getValue(j + 1) + count
      } else {
        nextLine[j] = nextLine.getValue(j) + count
      }
    }

    prevLine = nextLine
    nextLine = mutableMapOf<Int, Long>().withDefault { 0 }
  }

  println(prevLine.values.sum())
}