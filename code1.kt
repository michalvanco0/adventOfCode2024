import java.io.File
import kotlin.math.abs

fun main() {
    val lst1 = mutableListOf<Int>()
    val lst2 = mutableListOf<Int>()

    File("inputs\\input.txt").forEachLine { line ->
        val parts = line.trim().split("\\s+".toRegex())
        if (parts.size == 2) {
            val num1 = parts[0].toInt()
            val num2 = parts[1].toInt()
            lst1.add(num1)
            lst2.add(num2)
        }
    }
    lst1.sort()
    lst2.sort()
    var count = 0
    for (i in lst1.indices) {
        count += abs(lst1[i] - lst2[i])
    }
    println(count)

//    2
    var score = 0
    val appearances = lst2.groupingBy { it }.eachCount()
    for (i in lst1.indices) {
        score += lst1[i] * (appearances[lst1[i]] ?: 0)
    }
    println(score)
}
