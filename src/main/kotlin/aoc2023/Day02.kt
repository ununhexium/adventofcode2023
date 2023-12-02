package aoc2023

import loadData
import sanitizeInputLines
import java.lang.IllegalStateException

class Day02 {

  enum class Colour {
    RED,
    GREEN,
    BLUE
  }

  fun sumPossibleGameIds(text: String, limits: Map<Colour, Int>): Int {
    return sanitizeInputLines(text)
      .sumOf {
        val noGame = it.drop("Game ".length)
        val gameId = noGame.takeWhile { it != ':' }
        val noColumn = noGame.dropWhile { it != ' ' }
        val subsetsStr = noColumn.split(';')

        val possible = subsetsStr.all {
          val colors = it
            .split(',')
            .map { it.trim() }
            .map {
              val (countStr, colorStr) = it.split(' ')
              val color = when (colorStr) {
                "red" -> Colour.RED
                "green" -> Colour.GREEN
                "blue" -> Colour.BLUE
                else -> throw IllegalStateException("What is this color? $colorStr")
              }
              color to countStr.toInt()
            }

          colors.all { (color, count) ->
            limits.getOrDefault(color, 0) >= count
          }
        }

        if(possible) gameId.toInt() else 0
      }
  }

  fun fewestCubes(text: String): Int {
    return sanitizeInputLines(text)
      .sumOf {
        val noGame = it.drop("Game ".length)
        val gameId = noGame.takeWhile { it != ':' }
        val noColumn = noGame.dropWhile { it != ' ' }
        val subsetsStr = noColumn.split(';')

        var minRed = 0
        var minGreen = 0
        var minBlue = 0

        subsetsStr.map {
          it
            .split(',')
            .map { it.trim() }
            .forEach {
              val (countStr, colorStr) = it.split(' ')
              val count = countStr.toInt()
              when (colorStr) {
                "red" -> if (count > minRed) minRed = count
                "green" -> if (count > minGreen) minGreen = count
                "blue" -> if (count > minBlue) minBlue = count
                else -> throw IllegalStateException("What is this color? $colorStr")
              }
            }
        }

        minRed * minGreen * minBlue
      }
  }
}

fun main() {
  println(
    Day02().sumPossibleGameIds(
      loadData<Day02>(),
      mapOf(
        Day02.Colour.RED to 12,
        Day02.Colour.GREEN to 13,
        Day02.Colour.BLUE to 14,
      )
    )
  )

  println(
    Day02().fewestCubes(
      loadData<Day02>()
    )
  )
}