
fun main() {
    val board = readInput("Day06").map { it.toCharArray() }.toTypedArray()

    var startx = 0
    var starty = 0
    for(i in board.indices) {
        for(j in board[0].indices) {
            if (board[i][j] == '^') {
                startx = i
                starty = j
                break
            }
        }
    }
    val n = board.size
    val m = board[0].size
    println("Found starting position ($startx, $starty)")

    var i = startx
    var j = starty
    var direction = 0 // 0 - up, 1 - down, 2 - left, 3 - right
    var visited = mutableSetOf<Pair<Int,Int>>()

    while (i in 0..<n && j in 0..<m) {
        when {
            direction == 0 && i > 0 && board[i - 1][j] == '#'  -> direction = 3
            direction == 1 && i < n - 1 && board[i + 1][j] == '#'  -> direction = 2
            direction == 2 && j > 0 && board[i][j - 1] == '#'  -> direction = 0
            direction == 3 && j < m - 1 && board[i][j + 1] == '#'  -> direction = 1
            direction == 0 ->  visited.add(Pair(i,j)).also{i--}
            direction == 1 -> visited.add(Pair(i,j)).also{i++}
            direction == 2 -> visited.add(Pair(i,j)).also{j--}
            direction == 3 -> visited.add(Pair(i,j)).also{j++}
        }
    }
    println(visited.size)
    // part 2

    i = startx
    j = starty
    direction = 0 // 0 - up, 1 - down, 2 - left, 3 - right
    val visitedWithDirection = mutableSetOf<Triple<Int,Int,Int>>()
    val loopObstacles = mutableSetOf<Pair<Int,Int>>()
    val noLoops = mutableSetOf<Pair<Int, Int>>()
    while (i in 0..<n && j in 0..<m) {
        when {
            direction == 0 && i > 0 && board[i - 1][j] != '#'  -> {
                val obstacle = Pair(i - 1, j)
                board[i - 1][j] = '#'
                if (obstacle !in noLoops && verifyLoop(i, j, direction, board, visitedWithDirection)) {
                    loopObstacles.add(obstacle)
                } else {
                    noLoops.add(obstacle)
                }
                board[i - 1][j] = '.'
            }
            direction == 1 && i < n - 1 && board[i + 1][j] != '#'  -> {
                board[i + 1][j] = '#'
                val obstacle = Pair(i + 1, j)
                if (obstacle !in noLoops && verifyLoop(i, j, direction, board, visitedWithDirection)) {
                    loopObstacles.add(obstacle)
                } else {
                    noLoops.add(obstacle)
                }
                board[i + 1][j] = '.'
            }
            direction == 2 && j > 0 && board[i][j - 1] != '#'  -> {
                board[i][j - 1] = '#'
                val obstacle = Pair(i, j - 1)
                if (obstacle !in noLoops && verifyLoop(i, j, direction, board, visitedWithDirection)) {
                    loopObstacles.add(obstacle)
                } else {
                    noLoops.add(obstacle)
                }
                board[i][j - 1] = '.'
            }
            direction == 3 && j < m - 1 && board[i][j + 1] != '#'  -> {
                board[i][j + 1] = '#'
                val obstacle = Pair(i, j + 1)
                if (obstacle !in noLoops && verifyLoop(i, j, direction, board, visitedWithDirection)) {
                    loopObstacles.add(obstacle)
                } else {
                    noLoops.add(obstacle)
                }
                board[i][j + 1] = '.'
            }
        }
        when {
            direction == 0 && i > 0 && board[i - 1][j] == '#'  -> {
                direction = 3
                visitedWithDirection.add(Triple(i,j,0))
            }
            direction == 1 && i < n - 1 && board[i + 1][j] == '#'  -> {
                direction = 2
                visitedWithDirection.add(Triple(i,j,1))
            }
            direction == 2 && j > 0 && board[i][j - 1] == '#'  -> {
                direction = 0
                visitedWithDirection.add(Triple(i,j,2))
            }
            direction == 3 && j < m - 1 && board[i][j + 1] == '#'  -> {
                direction = 1
                visitedWithDirection.add(Triple(i,j,3))
            }
            direction == 0 ->  visitedWithDirection.add(Triple(i,j,0)).also{i--}
            direction == 1 -> visitedWithDirection.add(Triple(i,j,1)).also{i++}
            direction == 2 -> visitedWithDirection.add(Triple(i,j,2)).also{j--}
            direction == 3 -> visitedWithDirection.add(Triple(i,j,3)).also{j++}
        }
    }
    println(loopObstacles.size)
}

fun verifyLoop(startX: Int, startY: Int, startDirection: Int, board: Array<CharArray>, visited: Set<Triple<Int, Int, Int>>): Boolean {
    val visitedWithDirection = mutableSetOf<Triple<Int,Int,Int>>()
    visitedWithDirection.addAll(visited)
    val n = board.size
    val m = board[0].size
    var i = startX
    var j = startY
    var direction = startDirection
    var iter = 0
    while (i in 0..<n && j in 0..<m) {
        iter++
        if (visitedWithDirection.contains(Triple(i,j,direction))) {
            return true
        }
        when {
            direction == 0 && i > 0 && board[i - 1][j] == '#'  -> {
                direction = 3
                visitedWithDirection.add(Triple(i,j,0))
            }
            direction == 1 && i < n - 1 && board[i + 1][j] == '#'  -> {
                direction = 2
                visitedWithDirection.add(Triple(i,j,1))
            }
            direction == 2 && j > 0 && board[i][j - 1] == '#'  -> {
                direction = 0
                visitedWithDirection.add(Triple(i,j,2))
            }
            direction == 3 && j < m - 1 && board[i][j + 1] == '#'  -> {
                direction = 1
                visitedWithDirection.add(Triple(i,j,3))
            }
            direction == 0 -> visitedWithDirection.add(Triple(i,j,0)).also{i--}
            direction == 1 -> visitedWithDirection.add(Triple(i,j,1)).also{i++}
            direction == 2 -> visitedWithDirection.add(Triple(i,j,2)).also{j--}
            direction == 3 -> visitedWithDirection.add(Triple(i,j,3)).also{j++}
        }
    }
    return false
}