package day04

import solution

fun main() = solution { lines ->
  var answer = 0
  val papers = mutableSetOf<Pair<Int, Int>>()

  for (i in lines.indices) {
    for (j in lines[i].indices) {
      if (lines[i][j] == '@') papers.add(i to j)
    }
  }

  var changed = true
  while (changed) {
    changed = false
    papers.toList().forEach { (i, j) ->
      if (papers.nextPaperCount(i, j) < 4) {
        changed = true
        papers.remove(i to j)
        answer++
      }
    }
  }

  println(answer)
}

private fun Set<Pair<Int, Int>>.nextPaperCount(i: Int, j: Int): Int {
  var result = 0
  for (ii in i - 1..i + 1) {
    for (jj in j - 1..j + 1) {
      if (i == ii && j == jj) continue

      if (ii to jj in this) result += 1
    }
  }

  return result
}