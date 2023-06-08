package aoc19

import kotlin.math.abs

object Util {
    fun getInput(f: String): String? =
        this::class.java.getResource("/$f")?.readText()

    fun readIntCode(f: String): List<Long> =
        getInput(f)!!.split(",").map { it.trim().toLong() }

}

tailrec fun gcd(a: Int, b: Int): Int {
    if (a == 0) return b
    if (b == 0) return a

    val r = a % b
    return gcd(b, r)
}

tailrec fun gcd(a: Long, b: Long): Long {
    if (a == 0L) return b
    if (b == 0L) return a

    val r = a % b
    return gcd(b, r)
}

fun lcm(a: Int, b: Int): Int =
    lcm(a.toLong(), b.toLong()).toInt()

fun lcm(a: Long, b: Long): Long =
    abs(a) * abs(b) / gcd(a, b)
