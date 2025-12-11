package day11

import solution

const val END = "out"

fun main() = solution { lines ->
  val graph = lines
    .map { it.split(": ") }
    .associate { (device, outputs) -> device to outputs.split(" ").toSet() }
  val memory = mutableMapOf(END to 1)

  fun dfs(device: String): Int {
    if (device in memory) return memory.getValue(device)

    return graph
      .getValue(device)
      .sumOf { dfs(it) }
      .also { memory[device] = it }
  }

  println(dfs("you"))
}