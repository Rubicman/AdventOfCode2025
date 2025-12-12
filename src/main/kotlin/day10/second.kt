package day10

import gcd
import solution
import swap
import kotlin.math.absoluteValue

fun main() = solution { lines ->
  lines
    .map { it.toMachine() }
    .withIndex()
    .sumOf { (index, machine) ->
      print("Machine #${index + 1}: ")
      machine.solveMatrix()
    }
    .let { println(it) }
}

typealias Solutions = Sequence<Map<Int, Int>>

private fun Machine.solveMatrix(): Int {
  val n = joltages.size
  val m = buttons.size
  val matrix = MutableList(n) { i ->
    MutableList(m) { j ->
      if (i in buttons[j]) 1 else 0
    }.also { it.add(joltages[i]) }
  }

  gaussianElimination(matrix)
  val solutionSequence = allSolutions(matrix)

  val result = solutionSequence.minOf { it.values.sum() }
  println("solutions - ${solutionSequence.count()}, best solution - $result pushes")
  return result
}

private fun Machine.gaussianElimination(
  matrix: MutableList<MutableList<Int>>
) {
  val n = joltages.size
  val m = buttons.size

  // Cleaning all numbers below the diagonal
  var row = 0
  for (j in 0..<m) {
    if (row >= n) break
    var notZeroRow = -1
    for (i in row..<n) {
      if (matrix[i][j] != 0) notZeroRow = i
    }
    if (notZeroRow == -1) continue
    matrix.swap(row, notZeroRow)

    for (i in row + 1..<n) {
      matrix[i] = ((matrix[i] multiplyAll matrix[row][j]) minusAll (matrix[row] multiplyAll matrix[i][j])).compress()
    }

    row++
  }

  // Safety check. Shouldn't work during run.
  val noSolution = matrix
    .drop(m)
    .any { line -> !line.all { it == 0 } }
  if (noSolution)
    throw IllegalStateException("No solution: $this")

  // Clearing the maximum amount above the diagonal. This is optional and not needed
  for (i in n - 1 downTo 0) {
    var noZeroColumn = -1
    for (j in 0..<m) {
      if (matrix[i][j] != 0) {
        noZeroColumn = j
        break
      }
    }
    if (noZeroColumn == -1) continue

    for (ii in 0..<i) {
      matrix[ii] =
        ((matrix[ii] multiplyAll matrix[i][noZeroColumn]) minusAll (matrix[i] multiplyAll matrix[ii][noZeroColumn])).compress()
    }
  }
}

// Generating all possible solutions based on a Gaussian
private fun Machine.allSolutions(
  matrix: MutableList<MutableList<Int>>
): Solutions {
  val n = joltages.size
  val m = buttons.size

  // Push limits for every button
  val pushLimits = buttons.map { button -> button.minOf { joltages[it] } }
  var solutionSequence: Solutions = sequence { yield(emptyMap()) }
  // Buttons that are already in the sequence
  val buttonsInSolutions = mutableSetOf<Int>()

  for (i in n - 1 downTo 0) {
    var noZeroColumn = -1
    for (j in 0..<m) {
      if (matrix[i][j] != 0) {
        noZeroColumn = j
        break
      }
    }
    if (noZeroColumn == -1) continue

    for (j in noZeroColumn + 1..<m) {
      if (matrix[i][j] != 0 && j !in buttonsInSolutions) {
        buttonsInSolutions.add(j)
        solutionSequence = bruteforceButtons(j, pushLimits[j], solutionSequence)
      }
    }

    buttonsInSolutions.add(noZeroColumn)
    solutionSequence = solutionSequence.new { pushCounts ->
      var sum = matrix[i][m]
      for (j in noZeroColumn + 1..<m) {
        if (matrix[i][j] != 0) sum -= matrix[i][j] * pushCounts.getValue(j)
      }
      val value = sum / matrix[i][noZeroColumn]
      if (value >= 0 && value * matrix[i][noZeroColumn] == sum) {
        val newPushCount = pushCounts.toMutableMap()
        newPushCount[noZeroColumn] = value
        yield(newPushCount)
      }
    }
  }
  return solutionSequence
}

private fun <T> Sequence<T>.new(block: suspend SequenceScope<T>.(T) -> Unit): Sequence<T> =
  sequence { forEach { block(it) } }

private fun bruteforceButtons(index: Int, limit: Int, prev: Solutions): Solutions = sequence {
  prev.forEach { pushCounts ->
    bruteforceSingleButton(limit)
      .forEach { button ->
        val newPushCount = pushCounts.toMutableMap()
        newPushCount[index] = button
        yield(newPushCount)
      }
  }
}

private fun bruteforceSingleButton(limit: Int) = sequence {
  for (i in 0..limit) yield(i)
}

private infix fun List<Int>.multiplyAll(a: Int): MutableList<Int> = map { it * a }.toMutableList()
private infix fun List<Int>.minusAll(other: MutableList<Int>): MutableList<Int> =
  zip(other).map { (a, b) -> a - b }.toMutableList()

private fun MutableList<Int>.compress(): MutableList<Int> {
  val k = map { it.absoluteValue }
    .filter { it != 0 }
    .also { if (it.isEmpty()) return this }
    .reduce { a, b -> gcd(a, b) }

  if (k == 0)
    throw IllegalStateException("gcd is zero")

  return map { it / k }.toMutableList()
}