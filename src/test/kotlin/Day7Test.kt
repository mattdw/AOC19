import aoc19.Day7
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day7Test {

    @Test
    fun a() {
        assertEquals(567045, Day7.A())
    }

    @Test
    fun b() {
        assertEquals(39016654, Day7.B())
    }

    @Test
    fun perms() {
        assertEquals(listOf(
            listOf(1,2,3),
            listOf(1,3,2),
            listOf(2,1,3),
            listOf(2,3,1),
            listOf(3,2,1),
            listOf(3,1,2),
        ).toSet(), Day7.permutations(listOf(1,2,3)).toSet())
    }
}
