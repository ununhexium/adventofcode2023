package aoc2023

import loadData
import aoc2023.Day07.Rank.*
import sanitizeInputLines

class Day07 {

  enum class Card {
    A, K, Q, J, T, `9`, `8`, `7`, `6`, `5`, `4`, `3`, `2`
  }

  enum class Rank {
    FIVE_OF_A_KIND,
    FOUR_OF_A_KIND,
    FULL_HOUSE,
    THREE_OF_A_KIND,
    TWO_PAIRS,
    ONE_PAIR,
    HIGH_CARD,
  }

  data class Hand(val cards: List<Card>, val bet: Long) : Comparable<Hand> {

    val rank =
      when (cards.groupingBy { it }.eachCount().values.sortedDescending()) {
        listOf(5) -> FIVE_OF_A_KIND
        listOf(4, 1) -> FOUR_OF_A_KIND
        listOf(3, 2) -> FULL_HOUSE
        listOf(3, 1, 1) -> THREE_OF_A_KIND
        listOf(2, 2, 1) -> TWO_PAIRS
        listOf(2, 1, 1, 1) -> ONE_PAIR
        // setOf(1,1,1,1,1)
        else -> HIGH_CARD
      }

    override fun compareTo(other: Hand): Int =
      when {
        this.rank > other.rank -> 1
        this.rank < other.rank -> -1
        else -> this.cards
          .zip(other.cards)
          .firstOrNull { it.first != it.second }
          ?.let { it.first.compareTo(it.second) }
          ?: 0
      }

    override fun toString(): String {
      return "Hand(rank=$rank, cards=$cards, bet=$bet)"
    }


  }

  fun part1(text: String): Long {
    val lines = sanitizeInputLines(text)

    val hands = lines.map { l ->
      val (cards, bet) = l.split(Regex(" +"))

      Hand(
        cards.map { c ->
          Card.valueOf(c.toString())
        },
        bet.toLong()
      )
    }

    val sorted = hands.sorted()

    val ranked = sorted.zip(sorted.size downTo 1)

    return ranked.fold(0L) { acc, e ->
      acc + e.first.bet * e.second
    }
  }
}

fun main() {
  println(
    Day07().part1(
      loadData<Day07>()
    )
  )
}