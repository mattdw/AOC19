package aoc19

object Day8 {
    val input = Util.getInput("day8.txt")!!.trim().toList().map {
        it.toString().toInt()
    }

    fun A(): Int {
        val layerSize = 25 * 6
        val layers = input.windowed(layerSize, layerSize)

        val layerDetails = layers.map {
            it.groupBy { it }.mapValues {
                it.value.count()
            }
        }

        val keyLayer = layerDetails.minBy { it[0]!! }

        return keyLayer[1]!! * keyLayer[2]!!
    }

    fun B(): String {
        val layerSize = 25 * 6
        val layers = input.windowed(layerSize, layerSize).reversed()

        val pixels = layers.reduce { a, b ->
            a.zip(b) { a1, b1 ->
                if (b1 == 2)
                    a1
                else
                    b1
            }
        }

        var result = ""
        pixels.windowed(25, 25).forEach {
            result += it.map {
                when (it) {
                    0, 2 -> " "
                    1 -> "X"
                    else -> "?"
                }
            }.joinToString("").trimEnd()
            result += "\n"
        }

        return result
    }
}
