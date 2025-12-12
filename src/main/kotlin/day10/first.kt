package day10

import solution

val TARGET_REGEXP = """\[(?<target>[.#]+)]""".toRegex()
val BUTTON_REGEXP = """\((?<button>\d+(,\d+)*)\)""".toRegex()
val JOLTAGE_REGEXP = """\{(?<joltage>\d+(,\d+)*)}""".toRegex()

data class Machine(val target: List<Boolean>, val buttons: List<Set<Int>>, val joltages: List<Int>)

fun String.toMachine(): Machine {
  val target = TARGET_REGEXP.find(this)!!.groups["target"]!!.value.map { it == '#' }
  val buttons =
    BUTTON_REGEXP.findAll(this)
      .map { group -> group.groups["button"]!!.value.split(",").map { it.toInt() }.toSet() }
      .toList()
      .sortedBy { -it.size }
  val joltage = JOLTAGE_REGEXP.find(this)!!.groups["joltage"]!!.value.split(",").map { it.toInt() }

  return Machine(target, buttons, joltage)
}

fun main() = solution { lines ->
  lines
    .map { it.toMachine() }
    .sumOf { allVariants(it)!! }
    .let { println(it) }
}

private fun Machine.pushButtons(pressed: List<Int>): List<Boolean> {
  val state = MutableList(target.size) { false }

  pressed.forEach { buttonIndex ->
    buttons[buttonIndex].forEach { b ->
      state[b] = !state[b]
    }
  }

  return state
}

private fun allVariants(machine: Machine, buttonIndex: Int = 0, variant: List<Int> = emptyList()): Int? {
  if (buttonIndex == machine.buttons.size) {
    return if (machine.pushButtons(variant) == machine.target)
      variant.size
    else
      null
  }

  return listOfNotNull(
    allVariants(machine, buttonIndex + 1, variant),
    allVariants(machine, buttonIndex + 1, variant + buttonIndex)
  ).minOrNull()
}