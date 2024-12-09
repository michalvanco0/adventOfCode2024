import java.io.File

fun main() {
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    var numRows = 0
    var numCols = 0

    File("inputs\\input8.txt").forEachLine { line ->
        numCols = line.length
        line.forEachIndexed { col, char ->
            if (char != '.') {
                antennas.computeIfAbsent(char) { mutableListOf() }.add(numRows to col)
            }
        }
        numRows++
    }

    fun antinodes(antena1: Pair<Int, Int>, antena2: Pair<Int, Int>, pt2: Boolean = false): Set<Pair<Int, Int>> {
        val (x1, y1) = antena1
        val (x2, y2) = antena2
        val x = x1 - x2
        val y = y1 - y2

        val result = mutableSetOf<Pair<Int, Int>>()
        if (pt2) {
            result.add(antena1)
            result.add(antena2)
        }

        var newX1 = x1 + x
        var newY1 = y1 + y
        while (newX1 in 0 until numRows && newY1 in 0 until numCols) {
            result.add(newX1 to newY1)
            if (!pt2) break
            newX1 += x
            newY1 += y
        }

        var newX2 = x2 - x
        var newY2 = y2 - y
        while (newX2 in 0 until numRows && newY2 in 0 until numCols) {
            result.add(newX2 to newY2)
            if (!pt2) break
            newX2 -= x
            newY2 -= y
        }

        return result
    }

    fun <T> combinations(list: List<T>): List<Pair<T, T>> {
        val result = mutableListOf<Pair<T, T>>()
        for (i in list.indices) {
            for (j in i + 1 until list.size) {
                result.add(list[i] to list[j])
            }
        }
        return result
    }

    val unik = mutableSetOf<Pair<Int, Int>>()
    for (locations in antennas.values) {
        val pairs = combinations(locations)
        for ((u, v) in pairs) {
            unik.addAll(antinodes(u, v))
        }
    }
    println(unik.size)

    val unik2 = mutableSetOf<Pair<Int, Int>>()
    for (locations in antennas.values) {
        val pairs = combinations(locations)
        for ((u, v) in pairs) {
            unik2.addAll(antinodes(u, v, pt2 = true))
        }
    }
    println(unik2.size)
}
