fun main() {
    // part 1
    val lines = readInput("Day04")
    val characters = lines.map { it.toCharArray() }.toTypedArray()
    val n = characters.size
    val m = characters[0].size
    var hits = 0
    for (i in characters.indices) {
        for (j in characters[0].indices) {
            // horizontal
            if (j <= m - 4) {
                var matchedChars = ""
                for (k in 0..3) {
                    matchedChars += characters[i][j+k]
                }
                if (matchedChars == "XMAS" || matchedChars == "SAMX") {
                    hits++
                }
            }
            // down
            if (i <= n - 4) {
                var matchedChars = ""
                for (k in 0..3) {
                    matchedChars += characters[i + k][j]
                }
                if (matchedChars == "XMAS" || matchedChars == "SAMX") {
                    hits++
                }
            }
            // diagonal
            if (i <= n - 4 && j <= m - 4) {
                var matchedChars = ""
                for (k in 0..3) {
                    matchedChars += characters[i + k][j + k]
                }
                if (matchedChars == "XMAS" || matchedChars == "SAMX") {
                    hits++
                }
            }
            if (i <= n - 4 && j >= 3) {
                var matchedChars = ""
                for (k in 0..3) {
                    matchedChars += characters[i + k][j - k]
                }
                if (matchedChars == "XMAS" || matchedChars == "SAMX") {
                    hits++
                }
            }
        }
    }

    println(hits)
    hits = 0

    for (i in characters.indices) {
        for (j in characters[0].indices) {
            if (i <= n - 2 && j <= m - 2 && i >= 1 && j >= 1) {
                var matchedChars1 = ""
                var matchedChars2 = ""
                for (k in 0..2) {
                    matchedChars1 += characters[i + k - 1][j + k - 1]
                    matchedChars2 += characters[i + k - 1][j - k + 1]
                }
                if ((matchedChars1 == "MAS" || matchedChars1 == "SAM") && (matchedChars2 == "MAS" || matchedChars2 == "SAM")) {
                    hits++
                }
            }
        }
    }
    println(hits)
}
