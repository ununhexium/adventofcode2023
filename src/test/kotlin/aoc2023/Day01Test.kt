package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

class Day01Test : FunSpec({
  test("decode") {
    Day01().decode(
      """
      1abc2
      pqr3stu8vwx
      a1b2c3d4e5f
      treb7uchet
    """.trimIndent()
    ).shouldBeEqual(
      12 + 38 + 15 + 77
    )
  }

  test("decode2") {
    Day01().decode2(
      """
        5fivezgfgcxbf3five
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
      """.trimIndent()
    ).shouldBeEqual(
      55 + 29 + 83 + 13 + 24 + 42 + 14 + 76
    )
  }
})
