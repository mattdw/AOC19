package aoc19

import kotlin.math.*

object Day14 {
//    class Reaction(val from: String, val inputCount: Int, val to: String, val outputCount: Int)

    interface IReactor {
        fun produce(n: Long): Long
    }

    open class Reactor(val name: String) : IReactor {
        val inputs = mutableMapOf<String, Reactor>()
        var recipe: MutableMap<String, Long> = mutableMapOf()
        var batchSize: Long = 1
        var leftover: Long = 0

        override fun produce(n: Long): Long {
            var produced = 0L
            val toMake = n - leftover
            val fullBatches = toMake / batchSize
            val partialBatch = toMake.rem(batchSize)
            val batchesRequired = fullBatches + (if (partialBatch > 0) 1 else 0)
            if (batchesRequired > 0) {
                for (e in recipe.entries) {
                    inputs[e.key]!!.produce(e.value * batchesRequired)
                }
                leftover += batchSize * batchesRequired

            }

            val fromLeftover = min(n, leftover)
            leftover -= fromLeftover
            produced += fromLeftover

            assert(fromLeftover == n)
            assert(produced == n)
            return produced
        }

        override fun toString(): String {
            return "$batchSize $name < $recipe"
        }
    }

    class OreReactor : Reactor("ORE"), IReactor {
        var consumed = 0L

        override fun produce(n: Long): Long {
            consumed += n
            return n
        }
    }

    fun parseInput(s: String): Map<String, Reactor> {
        val reacts = mutableMapOf<String, Reactor>()

        fun getOrCreateReactor(name: String): Reactor =
            reacts.getOrPut(name) {
                if (name == "ORE")
                    OreReactor()
                else
                    Reactor(name)
            }

        s.trim().lines().map { line ->
            val (l, r) = line.split("=>").map { it.trim() }

            val (batchS, thisName) = r.trim().split(" ")
            val batchSize = batchS.toLong()

            val thisReact = getOrCreateReactor(thisName)
            thisReact.batchSize = batchSize

            for (comp in l.split(",")) {
                val (countS, name) = comp.trim().split(" ")
                val count = countS.toLong()
                val compReact = getOrCreateReactor(name)
                thisReact.inputs[name] = compReact
                thisReact.recipe[name] = count
            }

//            println(thisReact)
            Pair(r, thisReact)
        }.toMap()

        return reacts
    }

    fun A(input: String? = null): Long {
        val network = parseInput(input ?: Util.getInput("day14.txt")!!)

        network["FUEL"]?.produce(1)
        return (network["ORE"]!! as OreReactor).consumed
    }

    fun resetNetwork(net: Map<String, Reactor>) {
        net.values.forEach { reactor ->
            reactor.leftover = 0
            when (reactor) {
                is OreReactor -> reactor.consumed = 0
            }
        }
    }

    fun B(input: String? = null): Long {
        val network = parseInput(input ?: Util.getInput("day14.txt")!!)
        val ore = network["ORE"] as OreReactor
        val fuel = network["FUEL"] as Reactor

        var maxGuess = 9_999_999_999L
        var minGuess = 1L

        fun test(n: Long): Boolean {
            resetNetwork(network)
            fuel.produce(n)
            return (ore.consumed <= 1000000000000)
        }

        while ((maxGuess - minGuess) >= 2) {
            val currGuess = ((maxGuess - minGuess) / 2L) + minGuess

            if (test(currGuess)) {
                minGuess = currGuess
            } else {
                maxGuess = currGuess
            }
        }

        for (i in minGuess..maxGuess) {
            if (!test(i)) {
                return i - 1
            }
        }

        return -1
    }

}
