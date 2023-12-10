package aoc2023

import aoc2023.Rank.*
import aoc2023.WithJoker.J
import loadData
import sanitizeInputLines

interface Card {
  val ordinal: Int
}

enum class RegularCard : Card {
  A, K, Q, J, T, `9`, `8`, `7`, `6`, `5`, `4`, `3`, `2`
}

enum class WithJoker : Card {
  A, K, Q, T, `9`, `8`, `7`, `6`, `5`, `4`, `3`, `2`, J
}

enum class Rank {
  HIGH_CARD,
  ONE_PAIR,
  TWO_PAIRS,
  THREE_OF_A_KIND,
  FULL_HOUSE,
  FOUR_OF_A_KIND,
  FIVE_OF_A_KIND,
}

data class Hand<T : Card>(val cards: List<T>, val bet: Long)


class Day07 {

  fun <T : Card> getHandComparator(ranker: (Hand<T>) -> Rank): Comparator<Hand<T>> =
    Comparator { o1, o2 ->
      val o1Rank = ranker(o1)
      val o2Rank = ranker(o2)
      when {
        o1Rank > o2Rank -> -1
        o1Rank < o2Rank -> 1
        else -> o1.cards
          .zip(o2.cards)
          .firstOrNull { it.first.ordinal != it.second.ordinal }
          ?.let { it.first.ordinal.compareTo(it.second.ordinal) }
          ?: 0
      }
    }

  fun <T : Card> rank(cards: List<T>, grouper: (List<T>) -> List<Int>) =
    when (grouper(cards)) {
      listOf(5) -> FIVE_OF_A_KIND
      listOf(4, 1) -> FOUR_OF_A_KIND
      listOf(3, 2) -> FULL_HOUSE
      listOf(3, 1, 1) -> THREE_OF_A_KIND
      listOf(2, 2, 1) -> TWO_PAIRS
      listOf(2, 1, 1, 1) -> ONE_PAIR
      // 1,1,1,1,1
      else -> HIGH_CARD
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

    val sorted = hands.sortedWith(getHandComparator { it ->
      rank(it.cards) {
        it.groupingBy { it }.eachCount().values.sortedDescending()
      }
    })

    val ranked = sorted.zip(sorted.size downTo 1)

    return ranked.fold(0L) { acc, e ->
      acc + e.first.bet * e.second
    }
  }

  fun part2(text: String): Long {
    val lines = sanitizeInputLines(text)

    val hands = lines.map { l ->
      val (cards, bet) = l.split(Regex(" +"))

      Hand(
        cards.map { c ->
          WithJoker.valueOf(c.toString())
        },
        bet.toLong()
      )
    }

    val sorted = hands.sortedWith(getHandComparator { it ->
      rank(it.cards) {
        val upgraded = bruteForceUpgrade(it)
        if (upgraded != it && it.count { it == J } > 1) {
          println("Upgraded ${it.sorted()}")
          println("To       $upgraded")
        }
        upgraded.groupingBy { it }.eachCount().values.sortedDescending()
      }
    })

    val ranked = sorted.zip(sorted.size downTo 1)

    return ranked.fold(0L) { acc, e ->
      acc + e.first.bet * e.second
    }
  }

  private fun bruteForceUpgrade(cards: List<WithJoker>): List<WithJoker> {
    if (cards.groupBy { it }[J] == null) {
      return cards
    }

    // Feeling lazy, brute forcing

    val start = cards.sorted()
    var best = start
    var bestRank = rank(best) { it.groupingBy { it }.eachCount().values.sortedDescending() }

    val candidates = enumerate(start.toMutableList())
    candidates.forEach { alternative ->
      val candidateRank = rank(alternative) {
        it.groupingBy { it }.eachCount().values.sortedDescending()
      }
      if (candidateRank > bestRank) {
        best = alternative
        bestRank = candidateRank
      }
    }

    if (best.contains(J) && best.sorted() != cards.sorted()) {
      return bruteForceUpgrade(best)
    }

    return best
  }

  private fun enumerate(cards: MutableList<WithJoker>): List<List<WithJoker>> {
    if (!cards.contains(J)) {
      return listOf(cards)
    }
    val alternatives = mutableListOf<List<WithJoker>>()
    val firstJoker = cards.indexOf(J)
    WithJoker.entries.filter { it != J }.forEach { alternative ->
      val changed = cards.toMutableList()
      changed[firstJoker] = alternative
      alternatives.addAll(enumerate(changed))
    }

    return alternatives
  }

  private fun manualUpgrade(cards: List<WithJoker>): List<WithJoker> {
    val sorted = cards.sortedDescending()
    val jokers = sorted.count { it == J }
    val rank = rank(sorted) { it.groupingBy { it }.eachCount().values.sortedDescending() }
    val mut = sorted.toMutableList()
    val groups = sorted.groupBy { it }

    when (jokers) {
      1 -> {
        when (rank) {
          HIGH_CARD -> {
            // make a pair
            mut[0] = mut[1]
          }

          ONE_PAIR -> {
            // make a triple
            val best = groups.entries.first { it.value.size == 2 }.key
            mut[0] = best
          }

          TWO_PAIRS -> {
            // make full house
            val best = groups.entries.first { it.value.size == 2 }.key
            mut[0] = best
          }

          THREE_OF_A_KIND -> {
            // make quad
            val best = groups.entries.first { it.value.size == 3 }.key
            mut[0] = best
          }

          FULL_HOUSE -> error("Not possible")
          FOUR_OF_A_KIND -> {
            // make quint
            val best = groups.entries.first { it.value.size == 4 }.key
            mut[0] = best
          }

          FIVE_OF_A_KIND -> error("Not possible")
        }
      }

      2 -> {
        when (rank) {
          HIGH_CARD -> error("Not possible")

          ONE_PAIR -> {
            val best = groups.entries.first { it.value.size == 2 }.key
            if (best == J) {
              // make a triple
              val alt = groups.entries.first { it.value.size == 1 }.key
              val altIndex = sorted.indexOf(alt)
              mut[0] = mut[altIndex]
              mut[1] = mut[altIndex]
            } else {
              // make a quad
              mut[0] = best
              mut[1] = best
            }
          }

          TWO_PAIRS -> {
            // make full house
            val mvp = groups.entries.first { it.value.size == 2 }.key
            mut[0] = mvp
          }

          THREE_OF_A_KIND -> {
            // make quad
            val mvp = groups.entries.first { it.value.size == 3 }.key
            mut[0] = mvp
          }

          FULL_HOUSE -> error("Not possible")
          FOUR_OF_A_KIND -> {
            // make quint
            val mvp = groups.entries.first { it.value.size == 4 }.key
            mut[0] = mvp
          }

          FIVE_OF_A_KIND -> error("Not possible")
        }
      }
      // 0
      else -> cards
    }

    return mut
  }
}

fun main() {
  val result1 = Day07().part1(loadData<Day07>())

  if (result1 != 248217452L) {
    error("Something changed in the refactoring")
  }

  println(result1)

  println(Day07().part2(loadData<Day07>()))
}
