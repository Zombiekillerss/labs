package lab2

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths


class OpenCsvFiles(path: String) : OpenFile {
    var test: List<Address>

    init {
        test = readCsv(path)
    }


    private fun readCsv(inputStream: String): List<Address> {
        // read the file
        val reader = Files.newBufferedReader(Paths.get(inputStream))
        val csvParser = CSVParser(reader, CSVFormat.newFormat(';')).records.drop(1).map {
            Address(
                city = it.toList()[0],
                street = it.toList()[1],
                house = it.toList()[2].toInt(),
                floor = it.toList()[3].toInt()
            )
        }
        return csvParser
    }
}