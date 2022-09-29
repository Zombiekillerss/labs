package lab1

fun main() {
    print("Запрос: ")
    val request = readln()
    try {
        val searchResult = Search(request)

        println(searchResult.getPages())

        print("Вариант странички: ")
        val index = readln().toInt() - 1

        searchResult.openLinkIndex(index)
    } catch (e: java.lang.Exception) {
        println(e.message)
    } catch (e: java.lang.NumberFormatException) {
        println("Enter a number!")
    } catch (e: java.lang.IndexOutOfBoundsException) {
        println("Enter a valid page ID!")
    }
}