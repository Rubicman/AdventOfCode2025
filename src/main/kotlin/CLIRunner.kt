fun main(args: Array<String>) {
  if (args.isEmpty()) {
    println("Please specify day: ./gradlew run --args=10")
    return
  }

  val packageName = "day${args[0].padStart(2, '0')}"
  val part1Method = getMethod("$packageName.FirstKt")
  if (part1Method == null) {
    println("Wrong day: $args[0]. The solution may not have been implemented yet.")
    return
  }

  println("Day ${args[0]} problem")
  println("Part 1")
  part1Method.invoke(null, *Array(part1Method.parameters.size) { null })

  val part2Method = getMethod("$packageName.SecondKt") ?: return
  println()
  println("Part 2")
  part2Method.invoke(null, *Array(part2Method.parameters.size) { null })
}

fun getMethod(methodName: String) = try {
  ClassLoader.getSystemClassLoader().loadClass(methodName).methods.first { it.name == "main" }
} catch (_: ClassNotFoundException) {
  null
}