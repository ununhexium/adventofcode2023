package aoc2023

import loadData
import sanitizeInputLines

class Day04 {

  data class Card(val id: Int, val winning: List<Int>, val numbers: List<Int>) {
    val matches =
      numbers.count { it in winning }

    val points =
      when (matches) {
        0 -> 0
        else -> (1.shl(numbers.count { it in winning } - 1))
      }
  }

  private fun parseCards(lines: List<String>): List<Card> {
    val cards = lines.map { l ->
      val noCard = l.dropWhile { !it.isDigit() }
      val id = noCard.takeWhile { it.isDigit() }.toInt()
      val numbers = noCard.dropWhile { it.isDigit() }.dropWhile { !it.isDigit() }
      val sections = numbers.split("|")
      val winningSection = sections[0].trim().split(Regex(" +")).map { it.toInt() }
      val numbersSection = sections[1].trim().split(Regex(" +")).map { it.toInt() }

      Card(id, winningSection, numbersSection)
    }
    return cards
  }

  fun part1(text: String): Int {
    val lines = sanitizeInputLines(text)
    val cards = parseCards(lines)
    return cards.sumOf { it.points }
  }

  fun part2(text: String): Int {
    val lines = sanitizeInputLines(text)

    val cards = parseCards(lines)

    val scratchCount = cards.indices.map { 1 }.toMutableList()

    val sum = cards.indices.sumOf { i ->
      val card = cards[i]
      val range = (card.id + 1)..(card.id + card.matches)
      range.forEach {
        scratchCount[it - 1] += scratchCount[i]
      }
      scratchCount[i]
    }

    return sum
  }
}

fun main() {
  println(
    Day04().part1(loadData<Day04>())
  )
  println(
    Day04().part2(loadData<Day04>())
  )
}