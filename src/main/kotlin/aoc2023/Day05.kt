package aoc2023

import loadData
import sanitizeInputLines

class Day05 {

  enum class Section(val sectionName: String) {
    SeedToSoil("seed-to-soil map:"),
    SoilToFertilizer("soil-to-fertilizer map:"),
    FertilizerToWater("fertilizer-to-water map:"),
    WaterToLight("water-to-light map:"),
    LightToTemperature("light-to-temperature map:"),
    TemperatureToHumidity("temperature-to-humidity map:"),
    HumidityToLocation("humidity-to-location map:"),
    ;
  }

  data class Mapping(val dstStart: Long, val srcStart: Long, val length: Long) {
    constructor(all: List<Long>) : this(all[0], all[1], all[2])

    val inputs = srcStart..<(srcStart + length)
    val outputs = dstStart..<(dstStart + length)
    val diff = dstStart - srcStart

    fun contains(value: Long): Boolean =
      value in inputs

    fun map(value: Long): Long =
      value - srcStart + dstStart
  }


  fun part1(text: String): Long {
    val recipe = sanitizeInputLines(text)

    val seedsLine = recipe.iterator().next()
    val seeds = seedsLine.drop("seeds: ".length).split(" ").map { it.toLong() }

    val mappings = Section.entries.map { s ->
      s to recipe
        .dropWhile { it != s.sectionName }
        .takeWhile { it.isNotEmpty() }
        .drop(1)
        .map {
          Mapping(it.split(" ").map { it.toLong() })
        }
    }.toMap()

    val results = seeds.map {
      println("Mapping ${it}")
      val location = Section.entries.fold(it) { acc, e ->
        println("$e")
        val mapping = mappings[e]!!.firstOrNull { it.contains(acc) }
        val t = mapping?.map(acc) ?: acc
        println("$acc -> $t ${if (mapping == null) "without map" else "with ${mapping.diff} ${mapping.inputs} -> ${mapping.outputs}"}")
        t
      }
      println("Mapped $it to $location")
      it to location
    }

    return results.minBy { it.second }.second
  }
}

fun main() {
  println(
    Day05().part1(loadData<Day05>())
  )
}