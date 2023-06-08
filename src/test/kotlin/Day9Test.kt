import aoc19.Day9
import aoc19.IntCode
import aoc19.Util
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day9Test {

    @Test
    fun quineTest() {
        val prog = listOf<Long>(109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99)
        val m = IntCode(prog.toMutableList())
        m.run()
        assertEquals(prog, m.outputStream)
    }

    @Test
    fun output16Digits() {
        val m = IntCode(listOf<Long>(1102,34915192,34915192,7,4,7,99,0).toMutableList())
        m.run()
        println(m.outputStream)
        assertEquals(16, m.outputStream.last().toString().count())
    }

    @Test
    fun largeNumber() {
        val m = IntCode(listOf<Long>(104,1125899906842624,99).toMutableList())
        m.run()
        assertEquals(1125899906842624, m.outputStream.last())
    }

    @Test
    fun a() {
        assertEquals(2890527621, Day9.A())
    }

    @Test
    fun b() {
        assertEquals(66772, Day9.B())
    }
}
