package aoc2023

class Day01 {

  private val DIGITS = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
  private val DIGITS_OR_NAMED = listOf(
    "1" to '1',
    "2" to '2',
    "3" to '3',
    "4" to '4',
    "5" to '5',
    "6" to '6',
    "7" to '7',
    "8" to '8',
    "9" to '9',
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9'
  )

  private fun sanitizeInputLines(text: String) = text
    .split("\n")
    .map { it.trim() }
    .filter { it.isNotEmpty() }

  fun decode(text: String): Int =
    sanitizeInputLines(text)
      .sumOf { l -> "${l.first { it in DIGITS }}${l.last { it in DIGITS }}".toInt() }

  fun decode2(text: String): Int =
    sanitizeInputLines(text)
      .sumOf { l ->
        println(l)
        val firstDigit = DIGITS_OR_NAMED.filter { l.indexOf(it.first) >= 0 }.minBy { l.indexOf(it.first) }
        val lastDigit = DIGITS_OR_NAMED.maxBy { l.lastIndexOf(it.first) }
        println("$firstDigit $lastDigit")
        val combined = "${firstDigit.second}${lastDigit.second}"
        println("$combined")
        println("---")
        combined.toInt()
      }
}

fun main() {
  println(
    Day01().decode(
      Day01::class.java.getResource("/Day01.txt")!!.readText()
    )
  )

  println(
    Day01().decode2(
      Day01::class.java.getResource("/Day01.txt")!!.readText()
    )
  )
}