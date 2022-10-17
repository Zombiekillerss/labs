package lab2

class OpenAllFiles(path: String) {
    var datas: OpenFile? = null
    init {
        if(path.substring(path.length - 4) == ".csv" ||
            path.substring(path.length - 4) == ".xml") {
            if (path[path.length - 3] == 'c')
                datas = OpenCsvFiles(path)
            else if (path[path.length - 3] == 'x')
                datas = OpenXmlFiles(path)
        }
        else throw Exception("File is not csv or xml")
    }
}