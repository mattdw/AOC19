import aoc19.Day11
import aoc19.Point
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day11Test {

    @Test
    fun left() {
        assertEquals(Point(-1, 0), Point(0, 1).left())
    }

    @Test
    fun right() {
        assertEquals(Point(-1, 0), Point(0, -1).right())
    }

    @Test
    fun a() {
        assertEquals(2428, Day11.A())
    }

    @Test
    fun b() {
        assertEquals(
            """
  [][][]        [][]  []        [][][][]  [][][]    []    []    [][]    []    []
  []    []        []  []        []        []    []  []    []  []    []  []    []
  []    []        []  []        [][][]    [][][]    []    []  []        []    []
  [][][]          []  []        []        []    []  []    []  []        []    []
  []  []    []    []  []        []        []    []  []    []  []    []  []    []
  []    []    [][]    [][][][]  []        [][][]      [][]      [][]      [][]
        """
                .trim()
                .lines()
                .joinToString("\n", transform = String::trim),

            Day11.B()
                .trim()
                .lines()
                .joinToString("\n", transform = String::trim)
        )
    }
}
