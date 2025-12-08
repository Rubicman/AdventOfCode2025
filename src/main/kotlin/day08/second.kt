package day08

import solution
import kotlin.math.sign

fun main() = solution { lines ->
  val junctionBoxes = lines
    .mapIndexed { index, line ->
      line
        .split(',')
        .map(String::toLong)
        .let { (x, y, z) -> JunctionBox(x, y, z, index) }
    }
  val heap =
    Heap<Pair<JunctionBox, JunctionBox>> { a, b -> ((a.first distanceTo a.second) - (b.first distanceTo b.second)).sign }

  for (i in junctionBoxes.indices) {
    for (j in i + 1..<junctionBoxes.size) {
      heap.add(junctionBoxes[i] to junctionBoxes[j])
    }
  }

  val colorSizes = junctionBoxes.indices.associateWith { 1 }.toMutableMap()

  while (true) {
    val (a, b) = heap.pop()
    if (a.color == b.color) continue

    var newColor = a.color
    var oldColor = b.color

    if (colorSizes.getValue(a.color) < colorSizes.getValue(b.color)) {
      newColor = b.color
      oldColor = a.color
    }

    colorSizes[newColor] = colorSizes.getValue(newColor) + colorSizes.getValue(oldColor)
    colorSizes[oldColor] = 0

    junctionBoxes.forEach { if (it.color == oldColor) it.color = newColor }

    if (colorSizes[newColor] == junctionBoxes.size) {
      println(a.x * b.x)
      break
    }
  }
}