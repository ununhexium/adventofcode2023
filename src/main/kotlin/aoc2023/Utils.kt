inline fun <reified T> loadData() =
  T::class.java.getResource("/${T::class.simpleName}.txt")!!.readText()

fun sanitizeInputLines(text: String): List<String> = text
  .split("\n")
  .map { it.trim() }
  .dropLastWhile { it.isEmpty() }

fun <E> List<List<E>>.transposed(): List<List<E>> =
  List(this[0].size) { c ->
    List(this.size) { l ->
      this[l][c]
    }
  }
