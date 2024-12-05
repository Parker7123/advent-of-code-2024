fun main() {
    val lines = readInput("Day05")
    val splitIndex = lines.indexOf("")
    val rules = lines.take(splitIndex)
    val pageOrderings = lines.takeLast(lines.size - splitIndex - 1)

    val beforeRulDict = rules
        .map { it.split('|') }
        .groupBy(keySelector = { v -> v[1].toInt() }, valueTransform = { v -> v[0].toInt() })

    var sum = 0
    var sum2 = 0
    for (sequence in pageOrderings) {
        val splitSequence = sequence.split(',')
        val notAllowedPages: MutableSet<Int> = mutableSetOf()
        var sequenceAllowed = true
        for (page in splitSequence) {
            if (page.toInt() in notAllowedPages) {
                sequenceAllowed = false
                break
            }
            val notAllowedAfterPage = beforeRulDict.getOrDefault(page.toInt(), listOf())
            notAllowedPages.addAll(notAllowedAfterPage)
        }
        if (sequenceAllowed) {
            sum += splitSequence.middleElement().toInt()
        } else {
            val fixedSequence = fixSequence(splitSequence.map { it.toInt() }, beforeRulDict)
            sum2 += fixedSequence.middleElement().toInt()
        }
    }
    println("Etap 1")
    println(sum)
    println("Etap 2")
    println(sum2)
}

fun fixSequence(sequence: List<Int>, beforeRuleDict: Map<Int, List<Int>>): List<Int> {
    val outputSequence = mutableListOf<Int>()
    val availElements = sequence.toMutableSet()
    while (availElements.isNotEmpty()) {
        val currentElements = mutableListOf<Int>()
        currentElements.add(availElements.first())
        while (currentElements.isNotEmpty()) {
            val element = currentElements.last()
            val elementsBeforeElement = beforeRuleDict.getOrDefault(element, listOf())
                .filter { it in availElements }
            if (elementsBeforeElement.isNotEmpty()) {
                currentElements.addAll(elementsBeforeElement)
            } else {
                currentElements.removeLast()
                if (element in availElements) {
                    outputSequence.add(element)
                }
                availElements.remove(element)
            }
        }
    }
    return outputSequence
}
