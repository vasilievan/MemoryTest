import java.io.File
import java.util.*
import kotlin.math.pow
import kotlin.math.round

object Logic {
    fun tests (arraySize: Int, tries: Int) {
        var slower = 0
        var faster = 0
        val outputFile = File("output.txt")
        for (i in 0..tries) {
            var myArray: MutableList<MutableList<Byte?>>? = arrayGenerator(arraySize)
            val maxLength = myArray!!.sumBy {  it.count{ that -> that == null} }
            val arrayToAdd = toWrite(maxLength)
            val now = System.nanoTime()
            bruteForce(myArray, arrayToAdd, arraySize)
            val then = System.nanoTime() - now
            outputFile.appendText("Brute force: $then\n")
            val next = System.nanoTime()
            anotherVision(myArray, arrayToAdd, arraySize)
            val new = System.nanoTime() - next
            myArray = null
            System.gc()
            outputFile.appendText("Random: $new\n")
            val div = round(then.toDouble() / new.toDouble() * 100) / 100
            outputFile.appendText("BF/Random = $div\n")
            if (div > 1) {
                outputFile.appendText("BF is slower.\n\n")
                slower++
            } else {
                outputFile.appendText("BF is faster.\n\n")
                faster++
            }
        }
        outputFile.appendText("\nTOTAL RESULTS FOR A TRY\n")
        outputFile.appendText("Array size: ${2.0.pow(arraySize).pow(2.0)}, total tries: ${tries+1} \n")
        outputFile.appendText("BF is slower: $slower, BF is faster: $faster\n\n")
    }

    // генератор массива. Записывет байты в случайные ячейки
    fun arrayGenerator(lastIndex: Int): MutableList<MutableList<Byte?>>{
        val array = mutableListOf<MutableList<Byte?>>()
        val random = Random()
        for (i in 0..lastIndex) {
            val row = mutableListOf<Byte?>()
            for (j in 0..lastIndex){
                if (random.nextBoolean()) {
                    row.add((-128..127).random().toByte())
                } else {
                    row.add(null)
                }
            }
            array.add(row)
        }
        return array
    }

    // строка байтов, которую будем записывать
    fun toWrite(maxSize: Int): MutableList<Byte> {
        val array = mutableListOf<Byte>()
        for (i in 1..(1..maxSize).random()) {
            array.add((-128..127).random().toByte())
        }
        return array
    }

    // заполнение с начала в конец
    fun bruteForce(dataArr: MutableList<MutableList<Byte?>>, writeArr: MutableList<Byte>, lastIndex: Int): Set<Pair<Int, Int>> {
        val res = mutableSetOf<Pair<Int, Int>>()
        var k = 0
        for (i in 0..lastIndex) {
            for (j in 0..lastIndex) {
                if (k != writeArr.size) {
                    if (dataArr[i][j] == null) {
                        res.add(Pair(i, j))
                        k++
                    }
                } else {
                    return res
                }
            }
        }
        return res
    }

    // заполнение с псевдослучайным выбором ячейки
    fun anotherVision(dataArr: MutableList<MutableList<Byte?>>, writeArr: MutableList<Byte>, lastIndex: Int): Set<Pair<Int, Int>> {
        val res = mutableSetOf<Pair<Int, Int>>()
        var k = 0
        while (k != writeArr.size + 1) {
            val i = (lastIndex/2..lastIndex).random()
            val j = (lastIndex/2..lastIndex).random()
            if (dataArr[i][j] == null) {
                res.add(Pair(i, j))
                k++
            }
        }
        return res
    }
}