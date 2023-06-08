import aoc19.Day10
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun rat() {
        assertEquals(Day10.Rat(2, 10), Day10.Rat(1, 5))
    }

    @Test
    fun oppRat() {
        assertNotEquals(Day10.Rat(-2, 10), Day10.Rat(2, 10))
    }

    @Test
    fun oppRat2() {
        assertNotEquals(Day10.Rat(2, -10), Day10.Rat(2, 10))
        assertNotEquals(Day10.Rat(2, -10), Day10.Rat(-2, 10))
        assertNotEquals(Day10.Rat(2, -10), Day10.Rat(-2, -10))
    }

    @Test
    fun A() {
        assertEquals(221, Day10.A())
    }

    @Test
    fun B() {
        assertEquals(806, Day10.B())
    }
}
