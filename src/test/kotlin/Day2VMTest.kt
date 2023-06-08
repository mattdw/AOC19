import aoc19.Day2VM
import aoc19.day2A
import aoc19.day2B
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2VMTest {
    @Test
    fun runTests() {
        val m = Day2VM(mutableListOf<Int>(1,9,10,3,2,3,11,0,99,30,40,50))
        m.run()

        assertEquals(
            listOf(3500,9,10,70, 2,3,11,0, 99, 30,40,50),
            m.mem)
    }

    @Test
    fun tday2A() {
        assertEquals(3654868, day2A())
    }

    @Test
    fun tday2B() {
        assertEquals(7014, day2B())
    }
}
