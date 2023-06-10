package aoc19

import kotlin.math.*

object Day13 {
    val prog = Util.readIntCode("day13.txt")

    enum class BlockType {
        Wall, Block
    }

    class Map {
        val collisions = mutableMapOf<Point, BlockType>()
        var bounds = listOf(0, 0, 0, 0)
        var ball = Point(0,0)
        var paddle = Point(0, 0)
        var score = 0L

        fun update(data: List<Long>) {
            for ((x, y, t) in data.windowed(3, 3)) {
                val where = Point(x.toInt(), y.toInt())

                bounds = listOf(
                    min(bounds[0], where.x),
                    min(bounds[1], where.y),
                    max(bounds[2], where.x),
                    max(bounds[3], where.y),
                )

                if (where == Point(-1, 0)) {
                    score = t
                    continue
                }

                when (t.toInt()) {
                    0 -> collisions.remove(where)
                    1 -> collisions[where] = BlockType.Wall
                    2 -> collisions[where] = BlockType.Block
                    3 -> paddle = where
                    4 -> ball = where
                }
            }
        }

        override fun toString(): String {
            val sb = StringBuilder()
            sb.append("$score\n")
            (bounds[1]..bounds[3]).forEach { y ->
                (bounds[0]..bounds[2]).forEach { x ->
                    val where = Point(x, y)
                    if (ball == where) {
                        sb.append("()")
                    } else if (paddle == where) {
                        sb.append("==")
                    } else {
                        sb.append(when (collisions[where]) {
                            null -> "  "
                            BlockType.Wall -> "[]"
                            BlockType.Block -> "##"
                        })
                    }
                }
                sb.append('\n')
            }
            return sb.toString()
        }
    }

    fun buildMap(data: List<Long>): Map {
        val m = Map()
        m.update(data)
        return m
    }


    fun A(): Int {
        val m = IntCode(prog)
        m.run()

        val map = buildMap(m.outputStream)

        return map.collisions.filter { it.value == BlockType.Block }.count()
    }

    fun B(): Int {
        val m = IntCode(prog)
        m.mem[0] = 2
        val map = Map()

        while (!m.hasHalted) {
            m.run()
//            map.collisions.clear()
            map.update(m.outputStream)
            m.outputStream.clear()
//            println(map.toString())

            if (!map.collisions.values.any { it == BlockType.Block }) {
                break
            }

            m.inputStream.add((map.ball.x - map.paddle.x).sign.toLong())
        }

        return map.score.toInt()
    }
}
