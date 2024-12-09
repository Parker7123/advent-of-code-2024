import kotlin.math.abs
import kotlin.math.max

fun main() {
    val board = readInputToCharArray("Day08")
    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    val n = board.size
    val m = board[0].size

    for (i in board.indices) {
        for (j in board[0].indices) {
            if (board[i][j] != '.') {
                antennas.getOrPut(board[i][j]) { mutableListOf() }.add(Pair(i, j))
            }
        }
    }
//    println(antennas)
    val signalLocations = mutableSetOf<Pair<Int, Int>>()
    val signalLocations2 = mutableSetOf<Pair<Int, Int>>()
    for (list in antennas.values.filter { it.size > 1 }) {
        list.forEachIndexed { i, a1 ->
            list.drop(i + 1).forEach { a2 ->
                var diffY = a1.first - a2.first
                var diffX = a1.second - a2.second
                if ((a1.first + diffY).inRange(0, n) && (a1.second + diffX).inRange(0, m)) {
                    signalLocations.add(Pair(a1.first + diffY, a1.second + diffX))
                }
                if ((a2.first - diffY).inRange(0, n) && (a2.second - diffX).inRange(0, m)) {
                    signalLocations.add(Pair(a2.first - diffY, a2.second - diffX))
                }
                // part 2
                signalLocations2.add(a1)
                signalLocations2.add(a2)
                for (i in 1..max(n, m)) {
                    if ((a1.first + i * diffY).inRange(0, n) && (a1.second + i * diffX).inRange(0, m)) {
                        signalLocations2.add(Pair(a1.first + i * diffY, a1.second + i * diffX))
                    } else {
                        break
                    }
                }
                for (i in 1..max(n, m)) {
                    if ((a2.first - i * diffY).inRange(0, n) && (a2.second - i * diffX).inRange(0, m)) {
                        signalLocations2.add(Pair(a2.first - i * diffY, a2.second - i * diffX))
                    } else {
                        break
                    }
                }
            }
        }
    }
    // part 1
    println(signalLocations.size)
    // part 2
    println(signalLocations2.size)
}

fun Int.inRange(start: Int, endExcl: Int): Boolean {
    return this in start..<endExcl
}