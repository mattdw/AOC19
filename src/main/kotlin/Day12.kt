package aoc19

import kotlin.math.absoluteValue
import kotlin.text.*

data class Point3(val x: Long, val y: Long, val z: Long) {
    fun delta(other: Point3): Point3 =
        Point3(
            -x.compareTo(other.x).toLong(),
            -y.compareTo(other.y).toLong(),
            -z.compareTo(other.z).toLong()
        )

    operator fun plus(other: Point3): Point3 =
        Point3(x + other.x, y + other.y, z + other.z)

    override fun toString(): String =
        "($x, $y, $z)"
}

object Day12 {
    data class Moon(var loc: Point3, var vel: Point3) {
//        override fun toString(): String =
//            "<L$loc V$vel>"

        val potential: Long
            get() = loc.x.absoluteValue + loc.y.absoluteValue + loc.z.absoluteValue

        val kinetic: Long
            get() = vel.x.absoluteValue + vel.y.absoluteValue + vel.z.absoluteValue

        val energy: Long
            get() = potential * kinetic
    }

    val re = Regex("""<x=(-?\d+), y=(-?\d+), z=(-?\d+)>""")

    fun parse(s: String): List<Point3> =
        s.trim().lines().map {
            val match = re.matchEntire(it.trim())!!
            val groups = match.groupValues.drop(1).map(String::toLong)
            Point3(groups[0], groups[1], groups[2])
        }

    val test = """
        <x=-1, y=0, z=2>
        <x=2, y=-10, z=-7>
        <x=4, y=-8, z=8>
        <x=3, y=5, z=-1>
    """.trimIndent()

    val test2 = """
        <x=-8, y=-10, z=0>
        <x=5, y=5, z=10>
        <x=2, y=-7, z=3>
        <x=9, y=-8, z=-3>
    """.trimIndent()

    val final = """
        <x=-10, y=-10, z=-13>
        <x=5, y=5, z=-9>
        <x=3, y=8, z=-16>
        <x=1, y=3, z=-3>
    """.trimIndent()

    fun step(moons: List<Moon>) {
        moons.forEachIndexed { i, m1 ->
            moons.forEachIndexed { j, m2 ->
                m1.vel += m1.loc.delta(m2.loc)
            }
        }

        moons.forEach { m ->
            m.loc += m.vel
        }
    }

    fun A(): Long {
        val moons = parse(final).map {
            Moon(it, Point3(0, 0, 0))
        }

        repeat(1000) {
            step(moons)
        }

        return moons.sumOf { it.energy }
    }

    fun B(): Long {
        val moons = parse(final).map {
            Moon(it, Point3(0, 0, 0))
        }

        val periods = mutableListOf(0L, 0, 0)
        val individualStates = listOf(HashSet<List<Long>>(), HashSet(), HashSet())

        var steps = 0L
        while (periods.any { it == 0L }) {
            (0..2).map { axis ->
                if (periods[axis] != 0L) return@map
                val coords = when (axis) {
                    0 -> moons.flatMap { listOf(it.loc.x, it.vel.x) }
                    1 -> moons.flatMap { listOf(it.loc.y, it.vel.y) }
                    2 -> moons.flatMap { listOf(it.loc.z, it.vel.z) }
                    else -> listOf()
                }

                if (coords in individualStates[axis]) {
                    periods[axis] = steps
                } else {
                    individualStates[axis].add(coords)
                }
            }
            step(moons)
            steps++
        }

        println(periods)

        return periods.map { it }.reduce { a, b -> lcm(a, b) }
    }
}
