package day01

import solution

fun main() = solution { lines ->
  var position = 50
  var answer = 0

  for (line in lines) {
    var offset = line.substring(1).toInt()

    answer += offset / 100
    offset %= 100

    if (line[0] == 'L') offset *= -1

    if (position == 0 && offset < 0) answer--
    position += offset
    if (position == 0) answer++

    if (position < 0) {
      answer++
      position += 100
    }
    if (position >= 100) {
      answer++
      position -= 100
    }
  }

  println(answer)
}

