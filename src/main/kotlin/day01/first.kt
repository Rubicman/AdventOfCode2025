package day01

import solution

fun main() = solution { lines ->
  var position = 50
  var answer = 0

  for (line in lines) {
    var offset = line.substring(1).toInt()
    if (line[0] == 'L') offset *= -1

    position = (position + offset).withModulo()
    if (position == 0) answer++
  }

  println(answer)
}

fun Int.withModulo(): Int = (this % 100 + 100) % 100