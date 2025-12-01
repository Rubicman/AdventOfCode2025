import java.nio.file.Path
import kotlin.io.path.readLines

fun solution(body: (List<String>) -> Unit) {
  val folder = Path.of("src/main/resources/${getDay()}")
  println("---Test---")
  folder.resolve("test.txt").test(body)
  println("---Real---")
  folder.resolve("real.txt").test(body)
}

fun Path.test(body: (List<String>) -> Unit) = try {
  readLines().apply(body)
} catch (e: Exception) {
  println("Error: $e")
}

fun getDay(): String {
  val stackTrace = Throwable().stackTrace
  val regex = "day\\d{2}".toRegex()
  return regex.find(stackTrace[2].className)!!.value
}