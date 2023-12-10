package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day09Test : FunSpec({
  test("extrapolate 0, 0, 0, 0, 0") {
    Day09().extrapolate(
      listOf(0, 0, 0, 0, 0)
    ).shouldBe(0)
  }
  test("extrapolate 3, 3, 3, 3, 3") {
    Day09().extrapolate(
      listOf(3, 3, 3, 3, 3)
    ).shouldBe(3)
  }
  test("extrapolate 0, 3, 6, 9, 12, 15") {
    Day09().extrapolate(
      listOf(0, 3, 6, 9, 12, 15)
    ).shouldBe(18)
  }
  test("extrapolate 1, 3, 6, 10, 15, 21") {
    Day09().extrapolate(
      listOf(1, 3, 6, 10, 15, 21)
    ).shouldBe(28)
  }
  test("extrapolate 10, 13, 16, 21, 30, 45") {
    Day09().extrapolate(
      listOf(10, 13, 16, 21, 30, 45)
    ).shouldBe(68)
  }

  test("etalopartxe 10, 13, 16, 21, 30, 45") {
    Day09().etalopartxe(
      listOf(10, 13, 16, 21, 30, 45)
    ).shouldBe(5)
  }

  test("part1") {
    Day09().part1(
      """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
      """.trimIndent()
    ).shouldBe(114)
  }
})
