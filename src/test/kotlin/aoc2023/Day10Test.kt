package aoc2023

import aoc2023.Day10.Dir.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day10Test : FunSpec({
  test("part1") {
    Day10().part1(
      """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
      """.trimIndent()
    ).shouldBe(8)
  }

  test("part2 A") {
    Day10().part2(
      """
        ...........
        .S-------7.
        .|F-----7|.
        .||.....||.
        .||.....||.
        .|L-7.F-J|.
        .|..|.|..|.
        .L--J.L--J.
        ...........
      """.trimIndent(),
      S, Day10.Dir::left
    ).shouldBe(4)
  }
  test("part2 B") {
    Day10().part2(
      """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
      """.trimIndent(),
      S, Day10.Dir::left
    ).shouldBe(1)
  }
  test("part2 C") {
    Day10().part2(
      """
        ..........
        .S------7.
        .|F----7|.
        .||....||.
        .||....||.
        .|L-7F-J|.
        .|..||..|.
        .L--JL--J.
        ..........
      """.trimIndent(),
      S, Day10.Dir::left
    ).shouldBe(4)
  }
  test("part2 D") {
    Day10().part2(
      """
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ...
      """.trimIndent(),
      S, Day10.Dir::right
    ).shouldBe(8)
  }
  test("part2 E") {
    Day10().part2(
      """
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L
      """.trimIndent(),
      S, Day10.Dir::right
    ).shouldBe(10)
  }
})
