package aoc19

import kotlin.streams.toList

object Day4 {
    fun validPassword(n: Int): Boolean {
        val cs = n.toString().chars().toList()
        var hasPair = false
        cs.windowed(2, 1).forEach {
            hasPair = hasPair || it[0] == it[1]
            if (it[0] > it[1]) {
                return@validPassword false
            }
        }
        return hasPair
    }

    fun validPassword2(n: Int): Boolean {
        val cs = n.toString().toList()
        var hasPair = false
        var lastChar: Char = Char(0.toUShort())
        var groupLen = 0

        for (c in cs) {
            if (c < lastChar) {
                return false
            }
            if (lastChar == c) {
                groupLen++
            } else {
                if (groupLen == 2) {
                    hasPair = true
                }
                lastChar = c
                groupLen = 1
            }
        }
        if (groupLen == 2) {
            hasPair = true
        }

        return hasPair
    }

    fun A(): Int {
        return (248345..746315).filter {
            validPassword(it)
        }.count()
    }

    fun B(): Int {
        return (248345..746315).filter {
            validPassword2(it)
        }.count()
    }
}
