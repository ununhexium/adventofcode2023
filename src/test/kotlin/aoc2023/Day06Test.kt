package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Test : FunSpec({
  test("part1") {
    Day06().part1(
      listOf(
        Day06.Input(7,9),
        Day06.Input(15,40),
        Day06.Input(30,200),
      )
    ).shouldBe(
      288
    )
  }
  test("part2") {
    Day06().part1(
      listOf(
        Day06.Input(71530,940200),
      )
    ).shouldBe(
      71503
    )
  }
})
