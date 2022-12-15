package lab3

import java.io.File

class Opener {

    fun readDataFile(path: String): List<String> {
        return File(path).readLines()
    }

    fun getListData(path: String, delimiters: Char): MutableList<String> {
        return File(path).readText().split(delimiters).toMutableList()
    }

    fun updateData(path: String, data: String) {
        File(path).writeText(data)
    }
}