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

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
  this[index1] = this[index2].also { this[index2] = this[index1] }
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)