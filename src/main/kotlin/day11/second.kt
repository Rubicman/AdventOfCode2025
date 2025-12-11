package day11

import solution

data class State(
  val dac: Boolean,
  val fft: Boolean,
  val count: Long,
)

fun main() = solution { lines ->
  val graph = lines
    .map { it.split(": ") }
    .associate { (device, outputs) -> device to outputs.split(" ").toSet() }

  val memery = mutableMapOf(END to State(false, false, 1))
  fun dfs(device: String): State {
    if (device in memery) return memery.getValue(device)

    val states = graph
      .getValue(device)
      .map { dfs(it) }
      .map { if (device == "dac") it.copy(dac = true) else it }
      .map { if (device == "fft") it.copy(fft = true) else it }

    return (listOfNotNull(
      states.process(true, true),
      states.process(true, false),
      states.process(false, true),
      states.process(false, false),
    ).firstOrNull() ?: State(false, false, 1))
      .also { memery[device] = it }
  }

  println(dfs("svr").count)
}

private fun List<State>.process(dac: Boolean, fft: Boolean): State? =
  filter { it.dac == dac && it.fft == fft }
    .takeIf { it.isNotEmpty() }
    ?.sumOf { it.count }
    ?.let { State(dac, fft, it) }