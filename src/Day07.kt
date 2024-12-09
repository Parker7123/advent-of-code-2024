fun main() {
    val input = readInput("Day07")
    val sum = input.asSequence()
        .map { it.split(':') }
        .map { Pair(it[0].toLong(), it[1].trim().split(' ')) }
        .map {
            Pair(it.first, it.second.subList(1, it.second.size)
                .fold(listOf(it.second[0].toLong())) { acc, operand ->
                    acc.flatMap { cumSum ->
                        listOf(
                            cumSum + operand.toLong(),
                            cumSum * operand.toLong(),
                            (cumSum.toString() + operand).toLong()
                        )
                    }
                })
        }.filter { it.second.contains(it.first) }.sumOf { it.first }
    println(sum)
}