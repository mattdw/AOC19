package aoc19

class Day2VM(val mem: MutableList<Int>) {
    var pp = 0

    fun step(): Boolean {
        val op = mem[pp]
        when (op) {
            1 -> mem[mem[pp+3]] = mem[mem[pp+1]] + mem[mem[pp+2]]
            2 -> mem[mem[pp+3]] = mem[mem[pp+1]] * mem[mem[pp+2]]
            99 -> return false
        }
        pp += 4
        return true
    }

    fun run(): Int {
        while (step()) {
            continue
        }
        return mem[0]
    }

    fun fix() {
        fix(12, 2)
    }

    fun fix(noun: Int, verb: Int) {
        mem[1] = noun
        mem[2] = verb
    }
}

fun day2A(): Int {
    val codes = Util.getInput("day2.txt")?.trim()?.split(",")?.map {
        it.toInt()
    }

    val machine = Day2VM(codes!!.toMutableList())
    machine.fix()
    machine.run()
    return machine.mem[0]
}

fun day2B(): Int {
    val codes = Util.getInput("day2.txt")?.trim()?.split(",")?.map {
        it.toInt()
    }

    for (noun in 0..99) {
        for (verb in 0..99) {
            val machine = Day2VM(codes!!.toMutableList())
            machine.fix(noun, verb)
            machine.run()
            if (machine.mem[0] == 19690720) {
                return 100 * noun + verb
            }
        }
    }
    return -1
}
