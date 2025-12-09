package day09

import solution
import kotlin.math.absoluteValue

enum class Ceil {
  OUTSIDE, LINE, INSIDE
}

fun main() = solution { lines ->
  val points = lines.map { line -> line.split(",").map { it.toInt() }.let { Point(it[0], it[1]) } }

  val scaleX = points.map { it.x }.distinct().sorted().mapIndexed { index, x -> x to index }.toMap()
  val scaleY = points.map { it.y }.distinct().sorted().mapIndexed { index, y -> y to index }.toMap()

  fun Point.scaled(): Point {
    return Point(scaleX.getValue(x), scaleY.getValue(y))
  }

  val n = scaleX.values.max() + 1
  val m = scaleY.values.max() + 1
  val field = List(n) { MutableList<Ceil?>(m) { null } }

  for (i in points.indices) {
    val a = points[i].scaled()
    val b = points[(i + 1) % points.size].scaled()

    if (a.x == b.x && a.y != b.y) {
      for (y in minOf(a.y, b.y)..maxOf(a.y, b.y)) {
        field[a.x][y] = Ceil.LINE
      }
      continue
    }
    if (a.x != b.x && a.y == b.y) {
      for (x in minOf(a.x, b.x)..maxOf(a.x, b.x)) {
        field[x][a.y] = Ceil.LINE
      }
    }
  }

  val visited = mutableSetOf<Point>()
  fun dfs(current: Point): Ceil? {
    if (current.x !in field.indices) return Ceil.OUTSIDE
    if (current.y !in field[current.x].indices) return Ceil.OUTSIDE

    if (field[current.x][current.y] == Ceil.INSIDE) return Ceil.INSIDE
    if (field[current.x][current.y] == Ceil.OUTSIDE) return Ceil.OUTSIDE
    if (field[current.x][current.y] == Ceil.LINE) return null
    if (current in visited) return null

    visited.add(current)
    return (sequenceOf(
      Point(current.x - 1, current.y),
      Point(current.x + 1, current.y),
      Point(current.x, current.y - 1),
      Point(current.x, current.y + 1)
    )
      .map { dfs(it) }
      .firstOrNull { it != null }
      ?: Ceil.INSIDE)
      .also { field[current.x][current.y] = it }
  }

  for (x in field.indices) {
    for (y in field[x].indices) {
      if (field[x][y] == null) dfs(Point(x, y))
    }
  }

  var answer = 0L
  for (i in points.indices) {
    for (j in i + 1 until points.size) {
      val a = points[i].scaled()
      val b = points[j].scaled()

      var allInside = true
      for (x in minOf(a.x, b.x)..maxOf(a.x, b.x)) {
        for (y in minOf(a.y, b.y)..maxOf(a.y, b.y)) {
          if (field[x][y] == Ceil.OUTSIDE) allInside = false
        }
      }

      if (allInside) answer = maxOf(answer, area(points[i], points[j]))
    }
  }

  println(answer)
}

private fun area(a: Point, b: Point): Long {
  val (x1, y1) = a
  val (x2, y2) = b

  val width = (x1 - x2).absoluteValue + 1L
  val height = (y1 - y2).absoluteValue + 1L

  return width * height
}