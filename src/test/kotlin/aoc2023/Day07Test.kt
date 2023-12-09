package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day07Test : FunSpec({
  test("part1") {
    Day07().part1(
      """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
      """.trimIndent()
    ).shouldBe(
      6440
    )
  }
  test("part2") {
    Day07().part2(
      """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
      """.trimIndent()
    ).shouldBe(
      5905
    )
  }
})
