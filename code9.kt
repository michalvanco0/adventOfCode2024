import java.io.File
import java.math.BigInteger

fun main() {
    val input = File("inputs\\input9.txt").readText()
    val diskMap = input.trim().map { it.digitToInt() }

    var repre = createRepre(diskMap)
    move(repre)
    println(calcCheckSum(repre))

    repre = createRepre(diskMap)
    move2(repre)
    println(calcCheckSum(repre))
}

fun createRepre(diskMap: List<Int>): MutableList<BigInteger?> {
    val repre = mutableListOf<BigInteger?>()
    for ((i, size) in diskMap.withIndex()) {
        val value = if (i % 2 == 0) i / 2 else null
        for (j in 0 until size.toInt()) {
            if (value != null) {
                repre.add(value.toBigInteger())
            } else {
                repre.add(null)
            }
        }
    }
    return repre
}

fun move(repre: MutableList<BigInteger?>) {
    var start = 0
    var end = repre.size - 1
    while (start < end) {
        if (repre[end] == null) {
            end--
            continue
        }
        if (repre[start] != null) {
            start++
            continue
        }
        repre[start] = repre[end]
        repre[end] = null
        start++
        end--
    }
}

fun calcCheckSum(repre: List<BigInteger?>): BigInteger {
    var checkSum = BigInteger.ZERO
    for (i in repre.indices) {
        if (repre[i] == null) {
            break
        }
        checkSum += repre[i]?.times(i.toBigInteger()) ?: BigInteger.ZERO
    }
    return checkSum
}

fun move2(repre: MutableList<BigInteger?>) {
    val files = mutableMapOf<BigInteger, MutableList<Int>>()

    for (i in repre.indices) {
        repre[i]?.let {
            files.computeIfAbsent(it) { mutableListOf() }.add(i)
        }
    }

    for (fileID in files.keys.sortedDescending()) {
        val positions = files[fileID] ?: continue
        val size = positions.size
        var start = -1
        var freeSpace = 0
        for (i in repre.indices) {
            if (repre[i] == null) {
                freeSpace++
            } else {
                freeSpace = 0
            }
            if (freeSpace == size) {
                start = i - size + 1
                break
            }
        }

        if (start != -1 && start < positions.first()) {
            for (i in positions) repre[i] = null
            for (i in 0 until size) repre[start + i] = fileID
        }
    }
}

