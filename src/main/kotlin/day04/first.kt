package day04

import solution

fun main() = solution { lines ->
  var answer = 0
  for (i in lines.indices) {
    for (j in lines[i].indices) {
      if (lines[i][j] == '@' && lines.nextPaperCount(i, j) < 4) answer++
    }
  }

  println(answer)
}

private fun List<String>.nextPaperCount(i: Int, j: Int): Int {
  var result = 0
  for (ii in i - 1..i + 1) {
    if (ii !in indices) continue
    for (jj in j - 1..j + 1) {
      if (i == ii && j == jj) continue
      if (jj !in get(ii).indices) continue

      if (get(ii)[jj] == '@') result++
    }
  }

  return result
}