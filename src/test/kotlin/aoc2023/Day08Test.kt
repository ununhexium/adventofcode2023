package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class Day08Test : FunSpec({
  test("part1 Ex1") {
    Day08().part1(
      """
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
      """.trimIndent()
    ).shouldBe(2)
  }
  test("part1 Ex2") {
    Day08().part1(
      """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
      """.trimIndent()
    ).shouldBe(6)
  }
  test("part2") {
    Day08().part2(
      """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
      """.trimIndent()
    ).shouldBe(BigInteger.valueOf(6))
  }
})
