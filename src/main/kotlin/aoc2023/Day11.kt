package aoc2023

import loadData
import sanitizeInputLines
import transposed
import kotlin.math.abs

class Day11 {

  fun expand(text: String): List<List<Boolean>> {
    val grid = sanitizeInputLines(text).map { it.map { it == '#' } }

    val insertOnEmpty = { acc: List<List<Boolean>>, e: List<Boolean> ->
      if (e.none { it }) {
        acc + listOf(List(e.size) { false }) + listOf(e)
      } else {
        acc + listOf(e)
      }
    }
    val expandedByLine = grid.fold(listOf(), insertOnEmpty)

    val expandedByColumn = expandedByLine.transposed().fold(listOf(), insertOnEmpty).transposed()
    return expandedByColumn
  }

  fun part1(text: String): Int {
    val universe = expand(text)

    val galaxies = universe.flatMapIndexed { lx, l ->
      l.mapIndexedNotNull { cx, c ->
        if (c) {
          lx to cx
        } else null
      }
    }

    val pairs = galaxies.flatMapIndexed { gx, g1 ->
      galaxies.drop(gx + 1).map { g2 ->
        g1 to g2
      }
    }

    return pairs.sumOf { (g1, g2) ->
      abs(g1.first - g2.first) + abs(g1.second - g2.second)
    }
  }
}

fun main() {
  println(
    Day11().part1(
      loadData<Day11>()
    )
  )
}