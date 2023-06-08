package aoc19

import java.lang.Exception
import kotlin.math.max

class IntCode(memInit: List<Long>,
              val inputStream: MutableList<Long> = mutableListOf()) {

    enum class ParamMode {
        Immediate,
        Position,
        Relative
    }

    val mem = mutableMapOf<Long, Long>()
    val outputStream = mutableListOf<Long>()
    var programPointer = 0L
    var relativeBase = 0L
    var hasHalted = false

    init {
        memInit.forEachIndexed { i, v ->
            mem[i.toLong()] = v
        }
    }

    fun input(): Long {
        return inputStream.removeAt(0)
    }

    fun output(v: Long) {
        outputStream.add(v)
    }

    fun getModes(opcode: Long, count: Int): List<ParamMode> {
        var opcode = opcode / 100
        val modes = mutableListOf<ParamMode>()
        for (n in 0 until count.dec()) {
            when (opcode % 10) {
                0L -> modes.add(ParamMode.Position)
                1L -> modes.add(ParamMode.Immediate)
                2L -> modes.add(ParamMode.Relative)
            }
            opcode /= 10
        }
        return modes
    }

    fun getVal(addr: Long, mode: ParamMode): Long =
        mem[getAddr(addr, mode)] ?: 0

    fun getAddr(addr:Long, mode: ParamMode): Long =
        when (mode) {
            ParamMode.Position -> {
                mem[addr] ?: 0
            }
            ParamMode.Immediate -> addr
            ParamMode.Relative -> (mem[addr] ?: 0) + relativeBase
        }

//    fun getVal(addr: Int, mode: ParamMode): Long =
//        getVal(addr.toLong(), mode)

    fun step(): Boolean {
        val opcode = mem[programPointer] ?: 0
        var length = 1
        when ((opcode % 100).toInt()) {
            1 -> {
                length = 4
                val modes = getModes(opcode, length)
                mem[getAddr(programPointer+3, modes[2])] = getVal(programPointer+1, modes[0]) + getVal(programPointer+2, modes[1])
            }
            2 -> {
                length = 4
                val modes = getModes(opcode, length)
                mem[getAddr(programPointer+3, modes[2])] = getVal(programPointer+1, modes[0]) * getVal(programPointer+2, modes[1])
            }
            3 -> {
                if (inputStream.count() == 0) {
                    return false
                }
                length = 2
                val modes = getModes(opcode, length)
                mem[getAddr(programPointer+1, modes[0])] = input()
            }
            4 -> {
                length = 2
                val modes = getModes(opcode, length)
                output(getVal(programPointer+1, modes[0]))
            }
            5 -> {
                length = 3
                val modes = getModes(opcode, length)
                if (getVal(programPointer+1, modes[0]) != 0L) {
                    programPointer = getVal(programPointer+2, modes[1])
                    length = 0
                }
            }
            6 -> {
                length = 3
                val modes = getModes(opcode, length)
                if (getVal(programPointer+1, modes[0]) == 0L) {
                    programPointer = getVal(programPointer+2, modes[1])
                    length = 0
                }
            }
            7 -> {
                length = 4
                val modes = getModes(opcode, length)
                mem[getAddr(programPointer+3, modes[2])] = if (getVal(programPointer+1, modes[0]) < getVal(programPointer+2, modes[1])) 1 else 0
            }
            8 -> {
                length = 4
                val modes = getModes(opcode, length)
                mem[getAddr(programPointer+3, modes[2])] = if (getVal(programPointer+1, modes[0]) == getVal(programPointer+2, modes[1])) 1 else 0
            }
            9 -> {
                length = 2
                val modes = getModes(opcode, length)
                relativeBase += getVal(programPointer+1, modes[0])
            }

            99 -> {
                hasHalted = true
                return false
            }
        }
        programPointer += length
        return true
    }

    fun run(): Long {
        while (step()) {
            continue
        }
        return mem[0]!!
    }

    fun fix() {
        fix(12, 2)
    }

    fun fix(noun: Long, verb: Long) {
        mem[1] = noun
        mem[2] = verb
    }
}
