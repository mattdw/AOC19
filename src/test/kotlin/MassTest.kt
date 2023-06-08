import aoc19.Mass
import org.junit.jupiter.api.Assertions.*

class MassTest {

    @org.junit.jupiter.api.Test
    fun calcFuel() {
        assertEquals(2, Mass(12).calcFuel())
        assertEquals(2, Mass(14).calcFuel())
        assertEquals(654, Mass(1969).calcFuel())
        assertEquals(33583, Mass(100756).calcFuel())
    }
}
