package aoc2023

class Day06 {

  data class Input(val time: Long, val distance: Long) {
    fun numberOfWays(): Int =
      (0..time).count { t ->
        t * (time - t) >= distance
      }
  }

  fun part1(inputs: List<Input>): Long {
    return inputs.fold(1L) { acc, e ->
      acc * e.numberOfWays()
    }
  }
}

fun main() {

  val input = listOf(
    Day06.Input(53, 275),
    Day06.Input(71, 1181),
    Day06.Input(78, 1215),
    Day06.Input(80, 1524),
  )

  input.forEach {
    println(it.numberOfWays())
  }

  println(
    Day06().part1(input)
  )

  println(
    Day06.Input(53717880L, 275118112151524L).numberOfWays()
  )
}