import kotlin.math.pow

fun main() {
    for (i in 1..20) {
        Logic.tests(2.0.pow(i).toInt() - 1, 9)
    }
}