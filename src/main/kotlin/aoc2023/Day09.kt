package aoc2023

import loadData
import sanitizeInputLines

class Day09 {
  fun extrapolate(list: List<Int>): Int {

    val stack = ArrayDeque<List<Int>>()
    stack.addLast(list)

    while (!stack.last().all { it == 0 }) {
      stack.addLast(stack.last().zipWithNext().map { it.second - it.first })
    }

    val next = stack.reversed().map { it.last() }.sum()

    return next
  }

  fun etalopartxe(list: List<Int>): Int {

    val stack = ArrayDeque<List<Int>>()
    stack.addLast(list)

    while (!stack.last().all { it == 0 }) {
      stack.addLast(stack.last().zipWithNext().map { it.second - it.first })
    }

    val next = stack.reversed().map { it.first() }.fold(0) { acc, e ->
      e - acc
    }

    return next
  }

  fun part1(text: String): Int {
    return sanitizeInputLines(text)
      .map { it.split(" ").map { it.toInt() } }
      .map(this::extrapolate)
      .sum()
  }

  fun part2(text: String): Int {
    return sanitizeInputLines(text)
      .map { it.split(" ").map { it.toInt() } }
      .map(this::etalopartxe)
      .sum()
  }
}

fun main() {
  println(
    Day09().part1(
      loadData<Day09>()
    )
  )
  println(
    Day09().part2(
      loadData<Day09>()
    )
  )
}