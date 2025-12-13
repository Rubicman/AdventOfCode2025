package day12

import solution

class Region(val width: Int, val height: Int, val presentNumbers: List<Int>) {
  constructor(line: String) : this(
    width = line.takeWhile { it != 'x' }.toInt(),
    height = line.dropWhile { it != 'x' }.drop(1).takeWhile { it != ':' }.toInt(),
    presentNumbers = line.dropWhile { it != ':' }.drop(2).split(" ").map { it.toInt() }
  )
}

fun main() = solution { lines ->
  val regions = lines
    .dropWhile { 'x' !in it }
    .map { Region(it) }

  regions.count { field ->
    (field.width / 3) * (field.height / 3) >= field.presentNumbers.sum()
  }.let { println(it) }
}