package aoc19

import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

object Day3 {
    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point): Point =
            Point(x + other.x, y + other.y)

        operator fun times(other: Point): Point =
            Point(x * other.x, y * other.y)

        operator fun times(scale: Int): Point =
            Point(x * scale, y * scale)

        operator fun minus(other: Point): Point =
            Point(x - other.x, y - other.y)

        val angle: Double
            get() = atan2(x.toDouble(), y.toDouble())

        val length: Double
            get() = sqrt(x.toDouble().pow(2) + y.toDouble().pow(2))

        fun left(): Point =
            Point(-y, x)

        fun right(): Point =
            Point(y, -x)
    }

    enum class Direction {
        U, D, L, R;

        fun vector(): Point =
            when (this) {
                U -> Point(0, 1)
                D -> Point(0, -1)
                L -> Point(-1, 0)
                R -> Point(1, 0)
            }
    }

    data class Segment(val dir: Direction, val dist: Int)

    private fun parseInput(fname: String): List<List<Segment>> {
        val s = Util.getInput(fname)!!
        val lines = s.lines()

        return lines.map { line ->
            val steps = line.split(",")
                .filter { it.isNotEmpty() }
                .map { s ->
                    Segment(
                        Direction.valueOf(s.take(1)),
                        s.drop(1).toInt()
                    )
                }

            steps
        }
    }

    fun A(): Int {
        val wires = parseInput("day3.txt")

        val allVisits = wires.map { wire ->
            val visits = mutableSetOf<Point>()
            var here = Point(0, 0)
            for (segment in wire) {
                val dir = segment.dir.vector()

                repeat(segment.dist) {
                    here += dir
                    visits.add(here)
                }
            }
            visits
        }

        val nearCrossing = allVisits[0].intersect(allVisits[1]).map {
            it.x.absoluteValue + it.y.absoluteValue
        }.min()

        return nearCrossing
    }

    fun B(): Int {
        val wires = parseInput("day3.txt")

        val allVisits = wires.map { wire ->
            val visits = mutableMapOf<Point, Int>()
            var here = Point(0, 0)
            var steps = 0
            for (segment in wire) {
                val dir = segment.dir.vector()

                repeat(segment.dist) {
                    here += dir
                    steps++
                    if (here !in visits) {
                        visits[here] = steps
                    }
                }
            }
            visits
        }

        val nearCrossing = allVisits[0].keys.toSet().intersect(allVisits[1].keys.toSet()).map {
            allVisits[0][it]!! + allVisits[1][it]!!
        }.min()

        return nearCrossing
    }
}
