fun main() {
    val input = readInput("Day07")
    var sum = 0L
    for (equation in input.map { it.split(':') }) {
        val result = equation[0].toLong()
        val operands = equation[1].trim().split(' ')
        var results = listOf(operands[0].toLong())
        for (operand in operands.subList(1, operands.size)) {
            results = results.flatMap {
                listOf(
                    it + operand.toLong(),
                    it * operand.toLong(),
                    (it.toString() + operand).toLong()
                )
            }
        }
        if (results.contains(result)) {
            sum += result
        }
    }
    println(sum)
}