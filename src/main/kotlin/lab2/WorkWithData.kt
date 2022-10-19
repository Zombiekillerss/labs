package lab2

class WorkWithData(private val data: HashMap<Address, Int>) {
    fun printDublicateEntry() {
        println("Адресс и количество повторений: ")
        for (address in data) {
            println("${address.key} Повторений ${address.value}")
        }
    }

    fun printCountHouse() {
        val listHouse = mutableListOf<Int>()
        var countOne = 0
        var countTwo = 0
        var countThree = 0
        var countFour = 0
        var countFive = 0
        for (address in data) {
            if (!listHouse.contains(address.key.house)) {
                when (address.key.floor) {
                    1 -> countOne++
                    2 -> countTwo++
                    3 -> countThree++
                    4 -> countFour++
                    5 -> countFive++
                }
                listHouse.add(address.key.house)
            }
        }
        println("Выводим количество 1,2,3,4,5 этажных домов")
        println("1 этажных домов: $countOne")
        println("2 этажных домов: $countTwo")
        println("3 этажных домов: $countThree")
        println("4 этажных домов: $countFour")
        println("5 этажных домов: $countFive")
    }
}