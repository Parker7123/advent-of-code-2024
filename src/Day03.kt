import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    // part 1
    val regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)"
    val input = Path("src/Day03.txt").readText().lines().fold("") { acc: String, s: String -> acc + s }
    val mulRegex = Regex(regex)

     val p1Sum = mulRegex.findAll(input)
        .map { it.groupValues }.sumOf { it[1].toInt() * it[2].toInt() }
    println(p1Sum)

    // part 2
    val dontRegex = Regex("don't\\(\\)")
    val doRegex = Regex("do\\(\\)")
    var sum = 0
    var i = 0
    while (i < input.length) {
        val dontIndex = dontRegex.find(input, i)?.range?.endInclusive ?: input.length
        sum += mulRegex.findAll(input, i)
            .takeWhile { it.range.first < dontIndex }
            .map { it.groupValues }.sumOf { it[1].toInt() * it[2].toInt() }
        i = doRegex.find(input, dontIndex)?.range?.endInclusive ?: input.length
    }
    println(sum)
}
