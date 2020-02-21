package memory

import kotlin.math.pow

fun main() {
    for (i in 4..20) {
        Logic.tests(2.0.pow(i).toInt() - 1, 19)
    }
}