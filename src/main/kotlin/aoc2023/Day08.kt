package aoc2023

import loadData
import sanitizeInputLines

class Day08 {

  enum class Instruction {
    L,
    R
  }

  fun part1(text: String): Int {
    val lines = sanitizeInputLines(text)
    val instructions = lines[0].map { Instruction.valueOf(it.toString()) }
    val graph = lines.drop(2).map {
      val (node, left, right) = it.split(" = (", ", ", ")")
      node to (left to right)
    }.toMap()

    var current = "AAA"
    var ip = 0
    while (current != "ZZZ") {
      val i = instructions[ip % instructions.size]
      when (i) {
        Instruction.L -> current = graph[current]!!.first
        Instruction.R -> current = graph[current]!!.second
      }
      ip++
    }

    return ip
  }
}

fun main() {
  println(
    Day08().part1(
      loadData<Day08>()
    )
  )
}