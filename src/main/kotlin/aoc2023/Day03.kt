package aoc2023

import loadData
import sanitizeInputLines
import kotlin.math.max
import kotlin.math.min

class Day03 {

  data class NumberPosition(val line: Int, val start: Int, val end: Int = Int.MAX_VALUE) {
    fun contains(line: Int, col: Int): Boolean =
      this.line == line && col >= start && col <= end

    fun intValue(grid: List<String>): Int =
      grid[line].substring(start, end + 1).toInt()
  }

  fun part1(text: String): Int {
    val grid = sanitizeInputLines(text)
    val remainingNumbers = grid.flatMapIndexed { lineNo, l ->
      val numbers = extractNumbers(lineNo, l)
      numbers.filter { n ->
        nextToSymbol(n, grid)
      }
    }

    val sum = remainingNumbers.sumOf { n ->
      n.intValue(grid)
    }

    return sum
  }

  private val DIGITS = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

  private fun extractNumbers(lineNo: Int, l: String): List<NumberPosition> {
    val numbers = mutableListOf<NumberPosition>()
    var num: NumberPosition? = null

    l.forEachIndexed { i, c ->
      if (c in DIGITS) {
        if (num == null) num = NumberPosition(lineNo, i)
      } else {
        if (num != null) {
          numbers.add(num!!.copy(end = i - 1))
          num = null
        }
      }
    }

    if (num != null) {
      numbers.add(num!!.copy(end = l.lastIndex))
    }

    return numbers
  }

  private fun nextToSymbol(n: NumberPosition, grid: List<String>): Boolean {

    val startLine = max(0, n.line - 1)
    val endLine = min(n.line + 1, grid.lastIndex)
    val startColumn = max(0, n.start - 1)
    val endColumn = min(grid[0].lastIndex, n.end + 1)

    val notSymbol = DIGITS + '.'

    val touchesSymbol = (startLine..endLine).any { line ->
      (startColumn..endColumn).any { col ->
        grid[line][col] !in notSymbol
      }
    }

    return touchesSymbol
  }

  data class Position(val line: Int, val column: Int)

  fun part2(text: String): Int {

    val grid = sanitizeInputLines(text)

    val numbers = grid.flatMapIndexed { lineNo, l ->
      extractNumbers(lineNo, l)
    }

    val stars = grid.indices.flatMap { line ->
      grid[0].indices.mapNotNull { col ->
        if (grid[line][col] == '*') Position(line, col) else null
      }
    }

    val numbersAroundStars = stars.map { pos ->
      val startLine = max(0, pos.line - 1)
      val endLine = min(grid.lastIndex, pos.line + 1)
      val startColumn = max(0, pos.column - 1)
      val endColumn = min(grid[0].lastIndex, pos.column + 1)

      pos to (startLine..endLine).flatMap { line ->
        (startColumn..endColumn).mapNotNull { col ->
          numbers.firstOrNull { n -> n.contains(line, col) }
        }
      }.distinct()
    }

    val starsWith2Numbers = numbersAroundStars.filter { (_, numbers) -> numbers.size == 2 }

    return starsWith2Numbers.sumOf {
      it.second.fold(1 as Int) { acc, e ->
        acc * e.intValue(grid)
      }
    }
  }

}

fun main() {
  println(
    Day03().part1(
      loadData<Day03>()
    )
  )

  println(
    Day03().part2(
      loadData<Day03>()
    )
  )
}