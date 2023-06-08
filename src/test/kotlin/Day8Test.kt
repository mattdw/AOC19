import aoc19.Day8
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day8Test {

    @Test
    fun a() {
        assertEquals(2193, Day8.A())
    }

    @Test
    fun b() {
        val res = """
            X   XXXXX X  X XXXX XXXX
            X   XX    X  X X    X
             X X XXX  XXXX XXX  XXX
              X  X    X  X X    X
              X  X    X  X X    X
              X  XXXX X  X XXXX X
        """.trimIndent() + "\n"
        assertEquals(res, Day8.B())
    }
}
