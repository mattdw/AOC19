package aoc19

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

object Day10 {
    private val mapTest = """
        .#..##.###...#######
        ##.############..##.
        .#.######.########.#
        .###.#######.####.#.
        #####.##.#.##.###.##
        ..#####..#.#########
        ####################
        #.####....###.#.#.##
        ##.#################
        #####.##.###..####..
        ..######..##.#######
        ####.##.####...##..#
        .#####..#.######.###
        ##...#.##########...
        #.##########.#######
        .####.#.###.###.#.##
        ....##.##.###..#####
        .#.#.###########.###
        #.#.#.#####.####.###
        ###.##.####.##.#..##
    """.trimIndent().trim()

    private val map = """
        ###..#########.#####.
        .####.#####..####.#.#
        .###.#.#.#####.##..##
        ##.####.#.###########
        ###...#.####.#.#.####
        #.##..###.########...
        #.#######.##.#######.
        .#..#.#..###...####.#
        #######.##.##.###..##
        #.#......#....#.#.#..
        ######.###.#.#.##...#
        ####.#...#.#######.#.
        .######.#####.#######
        ##.##.##.#####.##.#.#
        ###.#######..##.#....
        ###.##.##..##.#####.#
        ##.########.#.#.#####
        .##....##..###.#...#.
        #..#.####.######..###
        ..#.####.############
        ..##...###..#########
    """.trimIndent().trim()

    private fun parseMap(m: String): List<Day3.Point> {
        val pts = mutableListOf<Day3.Point>()
        m.lines().forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '#') pts.add(Day3.Point(x, y))
            }
        }
        return pts.toList()
    }

    data class Rat(private var n: Int, private var d: Int) {
        init {
            val ns = n.sign
            val ds = d.sign
            val cd = gcd(n.absoluteValue, d.absoluteValue)
            n = n.absoluteValue / cd
            d = d.absoluteValue / cd

            n *= ns
            d *= ds
        }

        val numer: Int
            get() = n

        val denom: Int
            get() = d

        operator fun plus(other: Rat): Rat {
            val mul = lcm(this.denom, other.denom)
            return Rat(this.numer * mul / this.denom + other.numer * mul / this.denom, mul)
        }

        operator fun unaryMinus(): Rat =
            Rat(-numer, denom)

        operator fun minus(other: Rat): Rat =
            this + (-other)

        operator fun times(other: Rat): Rat =
            Rat(numer * other.numer, denom * other.denom)

        operator fun div(other: Rat): Rat =
            Rat(numer * other.denom, denom * other.numer)

        override fun toString(): String =
            "$numer/$denom"

    }

    fun A(): Int {
        val pts = parseMap(map)
        val out = pts.map { p ->
            val rels = pts.filter { it != p }.map {
                val vec = it - p
                Rat(vec.x, vec.y)
            }.toSet().count()
            Pair(p, rels)
        }

        val best = out.maxBy { it.second }
        println(best) // 11,11
        return best.second
    }


    private val test = Pair(mapTest, Day3.Point(11,13))
    private val final = Pair(map, Day3.Point(11, 11))

    fun B(): Int {
        val input = final

        val pts = parseMap(input.first)
        val origin = input.second
        val groups = pts.filter { it != origin }.map {
            it - origin
        }.groupBy { Rat(it.x, it.y) }

        var sortedgroups = groups
            .mapValues {
                it.value.sortedBy { p -> p.length.absoluteValue }
            }.values
            .sortedBy { -((it[0].angle + PI / 2) % (PI * 2)) }
            .map {ps -> ps.map {
                p -> p + origin
                }
            }

        // take all firsts
        // remove one by one n++
        // remove any empty lists
        // repeat

        var consumed = 0
        while (sortedgroups.isNotEmpty()) {
            val splits = sortedgroups.map {
                Pair(it.first(), it.drop(1))
            }
            val firsts = splits.map { it.first }

            // don't actually need to iterate, can just jump directly
            if (firsts.count() + consumed <= 200) {
                consumed += firsts.count()
            } else {
                val targ = firsts[200 - consumed.inc()]
                return targ.x * 100 + targ.y
            }

            sortedgroups = splits.map { it.second }.filter { it.isNotEmpty() }
        }
        println(sortedgroups)

        return -1
    }
}
