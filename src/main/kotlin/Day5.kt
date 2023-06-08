package aoc19

object Day5 {
    fun A(): Long {
        val mem = Util.readIntCode("day5.txt")
        val machine = IntCode(mem.toMutableList(), mutableListOf(1))
        machine.run()

        return machine.outputStream.last()
    }

    fun B(): Long {
        val mem = Util.readIntCode("day5.txt")
        val machine = IntCode(mem.toMutableList(), mutableListOf(5))
        machine.run()

        return machine.outputStream.last()
    }
}
