package aoc2023

import aoc2023.Day07.Rank.*
import loadData
import sanitizeInputLines

class Day07 {

  interface Card {
    val value: Int
  }

  enum class RegularCard(override val value: Int) : Card {
    A(14), K(13), Q(12), J(11), T(10), `9`(9), `8`(8), `7`(7), `6`(6), `5`(5), `4`(4), `3`(3), `2`(2)
  }

  enum class WithJokerCard(override val value: Int) : Card {
    A(14), K(13), Q(12), T(10), `9`(9), `8`(8), `7`(7), `6`(6), `5`(5), `4`(4), `3`(3), `2`(2), J(1)
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

  data class Hand(val cards: List<RegularCard>, val bet: Long) {

    companion object {
      fun rank(hand: Hand, grouper: (List<Card>) -> List<Int>) =
        when (grouper(hand.cards)) {
          listOf(5) -> FIVE_OF_A_KIND
          listOf(4, 1) -> FOUR_OF_A_KIND
          listOf(3, 2) -> FULL_HOUSE
          listOf(3, 1, 1) -> THREE_OF_A_KIND
          listOf(2, 2, 1) -> TWO_PAIRS
          listOf(2, 1, 1, 1) -> ONE_PAIR
          // 1,1,1,1,1
          else -> HIGH_CARD
        }

      fun getHandComparator(ranker: (Hand) -> Rank): Comparator<Hand> =
        Comparator { o1, o2 ->
          val o1Rank = ranker(o1)
          val o2Rank = ranker(o2)
          when {
            o1Rank > o2Rank -> 1
            o1Rank < o2Rank -> -1
            else -> o1.cards
              .zip(o2.cards)
              .firstOrNull { it.first != it.second }
              ?.let { it.first.compareTo(it.second) }
              ?: 0
          }
        }
    }

    fun toString(grouper: (List<Card>) -> List<Int>): String {
      return "Hand(rank=${rank(this, grouper)}, cards=$cards, bet=$bet)"
    }
  }

  fun part1(text: String): Long {
    val lines = sanitizeInputLines(text)

    val hands = lines.map { l ->
      val (cards, bet) = l.split(Regex(" +"))

      Hand(
        cards.map { c ->
          RegularCard.valueOf(c.toString())
        },
        bet.toLong()
      )
    }

    val sorted = hands.sortedWith(Hand.getHandComparator { it ->
      Hand.rank(it) {
        it.groupingBy { it }.eachCount().values.sortedDescending()
      }
    })

    val ranked = sorted.zip(sorted.size downTo 1)

    return ranked.fold(0L) { acc, e ->
      acc + e.first.bet * e.second
    }
  }
}

fun main() {
  val result1 = Day07().part1(loadData<Day07>())

  if (result1 != 248217452L) {
    error("Something changed in the refactoring")
  }

  println(
    result1
  )
}
