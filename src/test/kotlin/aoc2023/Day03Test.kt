package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class Day03Test : FunSpec({
  test("part1") {
    Day03().part1(
      """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
      """.trimIndent()
    ).shouldBeEqual(
      4361
    )
  }
  test("part2") {
    Day03().part2(
      """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
      """.trimIndent()
    ).shouldBeEqual(
      467835
    )
  }
})
