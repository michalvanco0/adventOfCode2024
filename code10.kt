import java.io.File

fun main() {
    val map = mutableListOf<List<Int>>()
    File("inputs\\input10.txt").forEachLine { line ->
        val valuse = line.map { it.digitToInt() }
        map.add(valuse)
    }
    val rows = map.size
    val cols = map[0].size
    val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))

    fun isValid(x: Int, y: Int, previous: Int): Boolean {
        return x in 0 until rows && y in 0 until cols && map[x][y] == previous + 1
    }

    fun bfs(startX: Int, startY: Int): Int {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(Pair(startX, startY))
        visited.add(Pair(startX, startY))
        val goal9 = mutableSetOf<Pair<Int, Int>>()
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            if (map[x][y] == 9) goal9.add(Pair(x, y))
            for ((dx, dy) in directions) {
                val newX = x + dx
                val newY = y + dy
                if (isValid(newX, newY, map[x][y]) && Pair(newX, newY) !in visited) {
                    visited.add(Pair(newX, newY))
                    queue.add(Pair(newX, newY))
                }
            }
        }
        return goal9.size
    }

    fun dfs(x: Int, y: Int): Int {
        if (map[x][y] == 9) return 1
        var rating = 0
        for ((dx, dy) in directions) {
            val newX = x + dx
            val newY = y + dy
            if (isValid(newX, newY, map[x][y])) {
                rating += dfs(newX, newY)
            }
        }
        return rating
    }

    var totalScore = 0
    var totalRating = 0
    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (map[i][j] == 0) {
                totalScore += bfs(i, j)
                totalRating += dfs(i, j)
            }
        }
    }
    println(totalScore)
    println(totalRating)
}