package lab2

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths

class OpenAllFiles(path: String) {
    lateinit var listData: HashMap<Address, Int>

    init {
        if (path.substring(path.length - 4) == ".csv" ||
            path.substring(path.length - 4) == ".xml"
        ) {
            if (path[path.length - 3] == 'c')
                listData = readCsv(path)
            else if (path[path.length - 3] == 'x')
                listData = readCsv(path)//OpenXmlFiles(path).test
        } else throw Exception("File is not csv or xml")
    }

    private fun readCsv(inputStream: String): HashMap<Address, Int> {
        val reader = Files.newBufferedReader(Paths.get(inputStream))
        val lkl = HashMap<Address, Int>()
        val csvParser = CSVParser(reader, CSVFormat.newFormat(';'))
        var newKey: Address
        csvParser.records.drop(1)
            .forEach {
                newKey = Address(it[0], it[1], it[2].toInt(), it[3].toInt())
                if (lkl.isEmpty() || !lkl.containsKey(newKey))
                    lkl[newKey] = 1
                else
                    lkl[newKey]?.let { it1 ->
                        lkl.replace(
                            newKey, it1, it1 + 1
                        )
                    }
            }
        return lkl
    }
}