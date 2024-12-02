import kotlin.math.abs
import kotlin.math.absoluteValue

const val PART_1_TEST_DATA = """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
"""

fun main() {

    println("Part 1")
    val test = PART_1_TEST_DATA.trimIndent().lines()
    println(part1(test))
    val input = readInput("Day02")
    println(part1(input))
    println("Part 2")
    println(part2(test))
    println(part2(input))
}

fun List<Int>.isStrictlyIncreasingDecreasingInBounds(from: Int, to: Int): Boolean {
    val partialSum = windowed(size = 2, step = 1)
        .fold(initial = 0, operation = { acc, tuple ->
            if (abs(tuple[0] - tuple[1]) !in from..to) {
                acc
            } else if (tuple[0] > tuple[1]) {
                acc - 1
            } else if (tuple[0] < tuple[1]) {
                acc + 1
            } else {
                acc
            }
        })
    return partialSum.absoluteValue == size - 1
}

fun part1(lines: List<String>): Int {
    return lines.map { it.split(' ') }.count {
        it.map { c -> c.toInt() }.isStrictlyIncreasingDecreasingInBounds(1, 3)
    }
}

// Goal was to do it in linear time
fun part2(lines: List<String>): Int {
    return lines.map { it.split(' ') }.count map@{ list ->
        val partialSumsEnd = list.map { c -> c.toInt() }
            .windowed(size = 2, step = 1)
            .runningFold(initial = 0, operation = { acc, tuple ->
                if (abs(tuple[0] - tuple[1]) !in 1..3) {
                    acc
                } else if (tuple[0] > tuple[1]) {
                    acc - 1
                } else if (tuple[0] < tuple[1]) {
                    acc + 1
                } else {
                    acc
                }
            })
        val n = partialSumsEnd.size
        val fullSum = partialSumsEnd.last()
        if (abs(fullSum) == n - 1) {
             return@map true
        }
        for (i in 0..<n) {
            if (i == 0) {
                val partialSum = fullSum - partialSumsEnd[1]
                if (abs(partialSum) == n - 2) {
                    return@map true
                }
            } else if (i < n - 1) {
                val leftSum = partialSumsEnd[i - 1]
                val rightSum = fullSum - partialSumsEnd[i + 1]
                var cumSum = leftSum + rightSum
                val a = list[i-1].toInt()
                val b = list[i+1].toInt()
                if (a < b) {
                    cumSum +=1
                } else if (a > b) {
                    cumSum -=1
                }
                if (abs(a - b) in 1..3 && abs(cumSum) == n - 2) {
                    return@map true
                }
            } else {
                val partialSum = partialSumsEnd[n - 2]
                if (abs(partialSum) == n - 2) {
                    return@map true
                }
            }
        }
        return@map false
    }
}

