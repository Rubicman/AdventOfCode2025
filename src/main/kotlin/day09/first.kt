package day09

import solution
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int)

fun main() = solution { lines ->
  val points = lines.map { line -> line.split(",").map { it.toInt() }.let { Point(it[0], it[1]) } }

  var result = 0L
  for (i in points.indices) {
    for (j in i + 1 until points.size) {
      result = maxOf(result, rectangle(points[i], points[j]))
    }
  }

  println(result)
}

private fun rectangle(a: Point, b: Point): Long {
  val (x1, y1) = a
  val (x2, y2) = b

  val width = (x1 - x2).absoluteValue + 1L
  val height = (y1 - y2).absoluteValue + 1L

  return width * height
}