package lab2

fun main() {
    println("Вводите\n1.Путь до файла с названием и расширением на конце\n2.e-выход")
    do {
        println("Путь до файла: ")
        val path = "e"//readln()
        if (path != "e") {
            val datas = OpenAllFiles(path)
            WorkWithFiles(datas)
        }
    } while (path != "e")
    println(OpenCsvFiles("C:\\Users\\sasha\\OneDrive\\Рабочий стол\\всякая всячина\\ООП\\address.csv").test)
}