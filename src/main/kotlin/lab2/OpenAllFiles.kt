package lab2

class OpenAllFiles(path: String) {
    private lateinit var data: OpenFile

    init {
        if (path.substring(path.length - 4) == ".csv" ||
            path.substring(path.length - 4) == ".xml"
        ) {
            if (path[path.length - 3] == 'c')
                data = OpenCsvFiles(path)
            else if (path[path.length - 3] == 'x')
                data = OpenXmlFiles(path)
        } else throw Exception("File is not csv or xml")
    }
}