package aoc19

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
