package day08

import solution
import kotlin.math.sign

class Heap<T>(private val comparator: Comparator<T>) {
  private val elements = mutableListOf<T>()

  private fun swap(i: Int, j: Int) {
    elements[i] = elements[j].also { elements[j] = elements[i] }
  }

  private fun pushUp(index: Int) {
    if (elements[index] < elements[index / 2]) {
      swap(index, index / 2)
      pushUp(index / 2)
    }
  }

  private fun pushDown(index: Int) {
    val left = index * 2
    val right = index * 2 + 1

    if (left > elements.lastIndex) return
    if (right > elements.lastIndex) {
      if (elements[index] > elements[left]) swap(index, left)
      return
    }
    if (elements[index] > elements[left] && elements[left] <= elements[right]) {
      swap(index, left)
      pushDown(left)
      return
    }
    if (elements[index] > elements[right] && elements[right] <= elements[left]) {
      swap(index, right)
      pushDown(right)
      return
    }
  }

  fun add(element: T) {
    elements.add(element)
    pushUp(elements.lastIndex)
  }

  fun pop(): T {
    val result = elements[0]
    swap(elements.lastIndex, 0)
    elements.removeLast()
    pushDown(0)
    return result
  }

  val size: Int get() = elements.size

  private operator fun T.compareTo(other: T): Int = comparator.compare(this, other)
}

class JunctionBox(val x: Long, val y: Long, val z: Long, var color: Int) {
  infix fun distanceTo(other: JunctionBox): Long {
    return (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y) + (z - other.z) * (z - other.z);
  }
}

const val CONNECTIONS = 1000

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

  repeat(CONNECTIONS) {
    if (heap.size == 0) return@repeat
    val (a, b) = heap.pop()
    if (a.color == b.color) return@repeat

    var newColor = a.color
    var oldColor = b.color

    if (colorSizes.getValue(a.color) < colorSizes.getValue(b.color)) {
      newColor = b.color
      oldColor = a.color
    }

    colorSizes[newColor] = colorSizes.getValue(newColor) + colorSizes.getValue(oldColor)
    colorSizes[oldColor] = 0

    junctionBoxes.forEach { if (it.color == oldColor) it.color = newColor }
  }

  println(colorSizes.values.sorted().takeLast(3).reduce { a, b -> a * b })
}