import kotlin.math.abs

fun splitListOfIds(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
    val l1: MutableList<Int> = mutableListOf()
    val l2: MutableList<Int> = mutableListOf()
    for (line in input) {
        val words = line.split("\\s+".toRegex())
        assert(words.size == 2)
        l1.add(words[0].toInt())
        l2.add(words[1].toInt())
    }
    return Pair(l1, l2)
}

fun main() {
    fun part1(input: List<String>): Int {
        val (l1, l2) = splitListOfIds(input)
        l1.sort()
        l2.sort()
        val sum = l1.zip(l2).sumOf { pair -> abs(pair.first - pair.second) }
        return sum
    }

    fun part2(input: List<String>): Int {
        val (l1, l2) = splitListOfIds(input)
        val frequencies = l2.groupingBy { id -> id }.fold(
            initialValue = 0,
            operation = { sum, _ -> sum + 1 }
        )

        return l1.sumOf { id -> frequencies.getOrDefault(id, 0) * id }
    }

    // Test if implementation meets criteria from the description, like:
    check(
        part1(
            listOf(
                "3   4",
                "4   3",
                "2   5",
                "1   3",
                "3   9",
                "3   3",
            )
        ) == 11
    )

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
