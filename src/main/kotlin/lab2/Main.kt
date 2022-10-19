package lab2

fun main() {
    println("Вводите\n1.Путь до файла с названием и расширением на конце\n2.e-выход")
    do {
        print("Путь до файла: ")
        var path = readln()
        if (path[path.length - 1] == 'e')
            path = "e"
        if (path != "e") {
            try {
                val start = System.currentTimeMillis()
                val data = OpenAllFiles(path)
                val work = WorkWithData(data.listData)
                work.printDublicateEntry()
                work.printCountHouse()
                val end = System.currentTimeMillis()
                println("Время обработки файла : " + (end - start) + " милисекунд")
            } catch (e: Exception) {
                println(e.message)
            }


        }
    } while (path != "e")
}