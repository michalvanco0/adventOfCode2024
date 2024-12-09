import java.io.File
import java.math.BigInteger

fun main() {
    var totalCalibration = BigInteger.ZERO
    var totalCalibration2 = BigInteger.ZERO
    File("inputs\\input7.txt").forEachLine { line ->
        val (resultStr, operandsStr) = line.trim().split(":")
        val expected = resultStr.toBigInteger()
        val operands = operandsStr.trim().split(" ").map{ it.toBigInteger()}

        val possibleOperators = mutableListOf<String>()
        val possibleOperators2 = mutableListOf<String>()
        val n = operands.size - 1

        fun gen(current: String) {
            if (current.length == n) {
                possibleOperators.add(current)
                return
            }
            gen("$current+")
            gen("$current*")
        }
        gen("")

        fun gen2(current: String) {
            if (current.length == n) {
                possibleOperators2.add(current)
                return
            }
            gen2("$current+")
            gen2("$current*")
            gen2("$current|")
        }
        gen2("")

        for (operators in possibleOperators) {
            var result = operands[0]
            for (i in 0 until n) {
                when (operators[i]) {
                    '+' -> result += operands[i + 1]
                    '*' -> result *= operands[i + 1]
                }
            }
            if (result == expected) {
                totalCalibration += result
                break
            }
        }

        for (operators in possibleOperators2) {
            var result = operands[0]
            for (i in 0 until n) {
                when (operators[i]) {
                    '+' -> result += operands[i+1]
                    '*' -> result *= operands[i+1]
                    '|' -> {
                        var concat = result.toString() + operands[i+1].toString()
                        result = concat.toBigInteger()
                    }
                }
                }
                if (result == expected) {
                    totalCalibration2 += result
                    break
                }
            }
    }
    println(totalCalibration)
    println(totalCalibration2)
}