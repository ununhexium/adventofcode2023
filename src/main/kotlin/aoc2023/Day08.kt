package aoc2023

import loadData
import sanitizeInputLines
import java.math.BigInteger

class Day08 {

  enum class Instruction {
    L,
    R
  }

  private fun parse(text: String): Pair<List<Instruction>, Map<String, Pair<String, String>>> {
    val lines = sanitizeInputLines(text)
    val instructions = lines[0].map { Instruction.valueOf(it.toString()) }
    val graph = lines.drop(2).map {
      val (node, left, right) = it.split(" = (", ", ", ")")
      node to (left to right)
    }.toMap()
    return Pair(instructions, graph)
  }

  fun part1(text: String): Int {
    val (instructions, graph) = parse(text)

    val ip = computeCycleLength(instructions, graph, "AAA") {
      it == "ZZZ"
    }

    return ip
  }

  private fun computeCycleLength(
    instructions: List<Instruction>,
    graph: Map<String, Pair<String, String>>,
    start: String,
    end: (String) -> Boolean
  ): Int {
    var current = start
    var ip = 0
    while (!end(current)) {
      val i = instructions[ip % instructions.size]
      when (i) {
        Instruction.L -> current = graph[current]!!.first
        Instruction.R -> current = graph[current]!!.second
      }
      ip++
    }
    return ip
  }

  fun part2(text: String): Long {
    val (instructions, graph) = parse(text)

    val starts = graph.keys.filter { it.endsWith("A") }
    val loops = starts.map {
      computeCycleLength(instructions, graph, it) {
        it.endsWith("Z")
      }
    }

    return loops.fold(1L) { acc, e ->
      findLCM(acc, e.toLong())
    }
  }

  /**
   * https://www.baeldung.com/kotlin/lcm
   */
  fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
      if (lcm % a == 0L && lcm % b == 0L) {
        return lcm
      }
      lcm += larger
    }
    return maxLcm
  }

}

fun main() {
  println(
    Day08().part1(
      loadData<Day08>()
    )
  )
  println(
    Day08().part2(
      loadData<Day08>()
    )
  )
}