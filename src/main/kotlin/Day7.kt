package aoc19

object Day7 {

    fun <T> permutations(items: List<T>): Sequence<List<T>> {
        return sequence<List<T>> {
            val items = items.toMutableList()
            val c = mutableListOf<Int>()
            for (i in 0 until items.count()) {
                c.add(0)
            }

            yield(items.toList())

            var i = 1
            while (i < items.count()) {
                if (c[i] < i) {
                    if (i % 2 == 0) {
                        val t = items[0]
                        items[0] = items[i]
                        items[i] = t
                    } else {
                        val t = items[c[i]]
                        items[c[i]] = items[i]
                        items[i] = t
                    }
                    yield(items.toList())
                    c[i] += 1
                    i = 1
                } else {
                    c[i] = 0
                    i += 1
                }
            }
        }
    }

    fun A(): Int {
        val code = Util.readIntCode("day7.txt")
        return permutations(listOf(0,1,2,3,4)).map { phases ->
            var signal = 0
            for (p in phases) {
                val machine = IntCode(
                    code.toMutableList(),
                    mutableListOf(p.toLong(), signal.toLong()))
                machine.run()
                signal = machine.outputStream.last().toInt()
            }
//            println("$phases -> $signal")
            signal
        }.max()
    }

    fun B(): Int {
        val code = Util.readIntCode("day7.txt")

        return permutations(listOf(5,6,7,8,9)).map { phases ->
            val machines = phases.map {
                IntCode(
                    code.toMutableList(),
                    mutableListOf(it.toLong()))
            }

            machines[0].inputStream.add(0)

            var curr = 0
            var prev = -1
            var signal = 0
            fun next() =
                (curr + 1) % machines.count()

            while (!machines[curr].hasHalted) {
                machines[curr].run()
                signal = machines[curr].outputStream.last().toInt()
                prev = curr
                curr = next()
                machines[curr].inputStream.add(signal.toLong())
            }

//            println("$phases -> $signal")
            signal
        }.max()
    }
}
