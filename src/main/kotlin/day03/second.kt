package day03

import solution

const val BATTERIES_COUNT = 12

fun main() = solution { lines ->
  lines.sumOf { line ->
    val memory = mutableMapOf<Pair<Int, Int>, String>()
    fun solve(start: Int, batteries: Int): String {
      if (start to batteries in memory) return memory.getValue(start to batteries)

      if (batteries == 0) return ""
      if (line.length - start == batteries) return line.substring(start)

      val withCurrent = solve(start + 1, batteries - 1).let { "${line[start]}$it" }
      val withoutCurrent = solve(start + 1, batteries)
      return listOf(withCurrent, withoutCurrent).max().also { memory[start to batteries] = it }
    }

    solve(0, BATTERIES_COUNT)!!.toLong()
  }
    .let { println(it) }
}