
inline fun <reified T> loadData() =
  T::class.java.getResource("/${T::class.simpleName}.txt")!!.readText()

fun sanitizeInputLines(text: String): List<String> = text
  .split("\n")
  .map { it.trim() }
  .dropLastWhile { it.isEmpty() }
