package aoc2023

import aoc2023.Day10.Dir.*
import aoc2023.Day10.Pipe.*
import loadData
import sanitizeInputLines

class Day10 {

  enum class Dir {
    N, E, S, W;

    fun right() =
      entries[(ordinal + 1) % entries.size]

    fun left() =
      entries[(entries.size + ordinal - 1) % entries.size]
  }

  enum class Pipe(val input: Char, val display: Char) {
    NORTH_SOUTH('|', '│'),
    EAST_WEST('-', '─'),
    NORTH_EAST('L', '└'),
    NORTH_WEST('J', '┘'),
    SOUTH_WEST('7', '┐'),
    SOUTH_EAST('F', '┌'),
    GROUND('.', '·'),
    START('S', '█'),
  }

  data class Point(val line: Int, val column: Int) {
    fun around(): List<Point> =
      listOf(
        Point(line + 1, column),
        Point(line - 1, column),
        Point(line, column + 1),
        Point(line, column - 1),
      )
  }

  fun part1(text: String): Int {
    val (grid, startLine, startColumn) = parse(text)
    val steps = walk(startLine, startColumn, grid, E)
    return steps / 2
  }

  private fun walk(
    startLine: Int,
    startColumn: Int,
    grid: List<List<Pipe>>,
    startDirection: Dir,
    onStep: (Int, Int, Dir) -> Unit = { _, _, _ -> },
  ): Int {
    var line = startLine
    var column = startColumn
    var steps = 0
    var direction = startDirection

    while (steps <= 0 || grid[line][column] != START) {
      onStep(line, column, direction)

      steps++
      // I know in which direction I start going
      // This is faster than enumerating all the cases to figure it out automatically
      when (direction) {
        N -> line--
        E -> column++
        S -> line++
        W -> column--
      }

      direction = when (grid[line][column]) {
        NORTH_SOUTH -> when (direction) {
          N -> direction
          E -> error("Impossible!")
          S -> direction
          W -> error("Impossible!")
        }

        EAST_WEST -> when (direction) {
          N -> error("Impossible!")
          E -> direction
          S -> error("Impossible!")
          W -> direction
        }

        NORTH_EAST -> when (direction) {
          N -> error("Impossible!")
          E -> error("Impossible!")
          S -> E
          W -> N
        }

        NORTH_WEST -> when (direction) {
          N -> error("Impossible!")
          E -> N
          S -> W
          W -> error("Impossible!")
        }

        SOUTH_WEST -> when (direction) {
          N -> W
          E -> S
          S -> error("Impossible!")
          W -> error("Impossible!")
        }

        SOUTH_EAST -> when (direction) {
          N -> E
          E -> error("Impossible!")
          S -> error("Impossible!")
          W -> S
        }

        GROUND -> error("Impossible!")
        START -> W // the start/end
      }
    }
    return steps
  }

  private fun parse(text: String): Triple<List<List<Pipe>>, Int, Int> {
    val lines = sanitizeInputLines(text)
    val grid = lines.map { l -> l.map { c -> Pipe.entries.first { it.input == c } } }
    val startLine = grid.indexOfFirst { it.contains(START) }
    val startColumn = grid[startLine].indexOfFirst { it == START }
    return Triple(grid, startLine, startColumn)
  }

  fun part2(text: String, startDirection: Dir, side: (Dir) -> Dir): Int {
    val (grid, startLine, startColumn) = parse(text)

    /*
     * The enclosed path is either on the left or on the right.
     * It's probably the smaller value or the one that doesn't blow up.
     * Compute both and choose the smaller one instead of trying to find on which side the enclosed stuff is.
     */

    val path = List(grid.size) { MutableList(grid[0].size) { false } }

    walk(startLine, startColumn, grid, startDirection) { line, column, _ ->
      path[line][column] = true
    }

    printPath(path, grid)

    val seeds = mutableListOf<Point>()

    // from that point, we came from the south, and now go east.
    // Now build a path that flags the points that are to the right side of the path
    walk(startLine, startColumn, grid, E) { line, column, direction ->
      seeds.add(Point(line, column))
      when (side(direction)) {
        N -> seeds.add(Point(line - 1, column))
        E -> seeds.add(Point(line, column + 1))
        S -> seeds.add(Point(line + 1, column))
        W -> seeds.add(Point(line, column - 1))
      }
    }

    println("Raw")
    printFlood(grid, seeds, path)

    val filtered = seeds.filterNot { path[it.line][it.column] }.distinct()

    println("Filtered")
    printFlood(grid, filtered, path)

    val size = floodFill(filtered, path, grid)

    return size
  }

  private fun floodFill(
    seeds: List<Point>,
    path: List<List<Boolean>>,
    grid: List<List<Pipe>>,
  ): Int {

    val todo = seeds.toMutableList()
    val done = List(grid.size) { MutableList(grid[0].size) { false } }
    val flooded = List(grid.size) { MutableList(grid[0].size) { false } }

    try {
      while (todo.isNotEmpty()) {
        val current = todo.removeLast()

        if (path[current.line][current.column]) continue
        if (done[current.line][current.column]) continue

        done[current.line][current.column] = true
        flooded[current.line][current.column] = true


        todo.addAll(current.around().filterNot { done[it.line][it.column] })
      }
    } catch (e: Exception) {
      println("Blew up!")
      printFlood(grid, todo, path, flooded)
      throw e
    }

    println("Found")
    printFlood(grid, todo, path, flooded)

    return flooded.sumOf { it.count { it } }
  }

  private fun printFlood(
    grid: List<List<Pipe>>,
    todo: List<Point>,
    path: List<List<Boolean>>,
    flooded: List<List<Boolean>>? = null
  ) {
    grid.forEachIndexed { lx, line ->
      line.forEachIndexed { cx, cell ->
        val point = Point(lx, cx)
        val color = when {
          flooded?.let { it[lx][cx] } == true -> "\u001b[1;36m"
          point in todo -> "\u001b[0;32m"
          path[lx][cx] -> "\u001b[0;31m"
          else -> "\u001b[0m"
        }
        print(color)
        if (path[lx][cx]) {
          print(cell.display)
        } else {
          if (flooded?.let { it[lx][cx] } == true) {
            print("X")
          } else {
            print(".")
          }

        }

      }
      println()
    }
  }

  private fun printPath(path: List<List<Boolean>>, grid: List<List<Pipe>>) {
    path.forEachIndexed { lx, line ->
      line.forEachIndexed { cx, cell ->
        if (cell) {
          print(grid[lx][cx].display)
        } else {
          print(".")
        }
      }
      println()
    }
  }
}

fun main() {
  println(
    Day10().part1(
      loadData<Day10>()
    )
  )
  println(
    Day10().part2(
      loadData<Day10>(), N, Day10.Dir::left
    )
  )
}