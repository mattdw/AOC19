package aoc19

object Day6 {
    class Body(val name: String, var parent: Body?) {
        val allParents: List<Body>
            get() {
                val s = mutableListOf<Body>()
                var it = this
                while (it.parent != null) {
                    s.add(it.parent!!)
                    it = it.parent!!
                }
                return s.toList()
            }
    }

    fun A(): Int {
        val lines = Util.getInput("day6.txt")!!.trim().lines()
        val bodies = mutableMapOf<String, Body>()

        fun getOrAdd(name: String): Body {
            if (!bodies.containsKey(name)) {
                bodies[name] = Body(name, null)
            }
            return bodies[name]!!
        }

        lines.forEach {
            val names = it.split(")").map { it.trim() }
            val b1 = getOrAdd(names[0])
            val b2 = getOrAdd(names[1])
            b2.parent = b1
        }

        val orbitCount = bodies.values.map {
            var n = 0
            var it = it
            while (it.parent != null) {
                n++
                it = it.parent!!
            }
            n
        }.sum()

        return orbitCount
    }

    fun B(): Int {
        val lines = Util.getInput("day6.txt")!!.trim().lines()
        val bodies = mutableMapOf<String, Body>()

        fun getOrAdd(name: String): Body {
            if (!bodies.containsKey(name)) {
                bodies[name] = Body(name, null)
            }
            return bodies[name]!!
        }

        lines.forEach {
            val names = it.split(")").map { it.trim() }
            val b1 = getOrAdd(names[0])
            val b2 = getOrAdd(names[1])
            b2.parent = b1
        }

        val me = bodies["YOU"]!!
        val santa = bodies["SAN"]!!

        val meps = me.allParents
        val saps = santa.allParents

        meps.forEachIndexed { i, b1 ->
            saps.forEachIndexed { j, b2 ->
                if (b1 == b2) {
                    return@B i + j
                }
            }
        }

        return -1
    }
}
