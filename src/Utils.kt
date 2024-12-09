import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> List<T>.middleElement() = get(size / 2)

fun readInputToCharArray(name: String): Array<CharArray> = readInput(name).map { it.toCharArray() }.toTypedArray()

fun Array<CharArray>.prettyPrint() {
    for (i in indices) {
        val array = get(i)
        for (j in array.indices) {
            print(array[j])
        }
        println("")
    }
}