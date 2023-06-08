package aoc19

object Day9 {
    val prog = Util.readIntCode("day9.txt")

    fun A(): Long {
        val m = IntCode(prog, listOf(1L).toMutableList())
        m.run()
        return m.outputStream.last()
    }

    fun B(): Long {
        val m = IntCode(prog, listOf(2L).toMutableList())
        m.run()
        return m.outputStream.last()
    }
}
