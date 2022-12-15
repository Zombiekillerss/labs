package lab3

import lab4.SQLDatabaseConnection
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class Handler {
    private val dataMap = mutableMapOf<String, MutableList<Schedule>>()

    fun getStructureInformationFromFile(
        pathToTimeTable: String,
        pathToWeek: String,
        opener: Opener,
        day: String
    ): String {
        val dataLines = opener.readDataFile(pathToTimeTable)
        val currentWeek = getCurrentWeekFromFile(pathToWeek, opener)
        processData(dataLines, currentWeek)

        return getString(day, dataMap)
    }

    fun getStructureInformationFromSQL(
        sqlConnection: SQLDatabaseConnection,
        day: String,
        group: String
    ): String {
        val parityWeek = getCurrentWeekSQL(sqlConnection)
        processData(sqlConnection.getInformation(group, parityWeek, day), parityWeek)
        return getString(day, dataMap)
    }

    private fun getCurrentWeekSQL(sqlConnection: SQLDatabaseConnection): String {
        val dateFormatInput = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dataWeek = processParity(sqlConnection.getInformationParity(), dateFormatInput)
        sqlConnection.updateParity(dataWeek[0], dataWeek[1])
        return dataWeek[0]
    }

    private fun processData(
        dataLines: List<String>,
        currentWeek: String,
    ) {
        var currentDay = ""
        for (i in dataLines) {
            if (i[0] == '1' || i[0] == '2') {
                if (i[0] == currentWeek[0]) {
                    val dataNow = i.trim().split(':').toMutableList()
                    var j = 1
                    if (dataNow.size > 2) {
                        while (j < dataNow.size) {
                            dataMap[currentDay]?.add(
                                Schedule(
                                    dataNow[j],
                                    dataNow[j + 1],
                                    dataNow[j + 2],
                                    dataNow[j + 3],
                                    dataNow[j + 4],
                                    currentWeek
                                )
                            )
                            j += 5
                        }
                    }
                }
            } else {
                when (i) {
                    "пн" -> currentDay = "Понедельник"
                    "вт" -> currentDay = "Вторник"
                    "ср" -> currentDay = "Среда"
                    "чт" -> currentDay = "Четверг"
                    "пт" -> currentDay = "Пятница"
                    "су" -> currentDay = "Суббота"
                }
                dataMap[currentDay] = mutableListOf()
                continue
            }
        }
    }

    private fun getString(day: String, mapData: Map<String, List<Schedule>>): String {
        var result = ""
        result += "*$day*\n"
        for (j in mapData[day]?.indices!!)
            result += "*Пара №${j + 1}*\n ${mapData[day]?.get(j)} \n"
        return result
    }

    private fun getCurrentWeekFromFile(pathToWeek: String, opener: Opener): String {
        val dateFormatInput = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val listWeek = processParity(opener.getListData(pathToWeek, '\n'), dateFormatInput)
        opener.updateData(pathToWeek, listWeek[0] + "\n" + listWeek[1])
        return listWeek[0]
    }

    private fun processParity(
        dataNow: MutableList<String>,
        dateFormatInput: DateTimeFormatter?,
    ): List<String> {
        val dateNow = LocalDate.parse(dataNow[1], dateFormatInput)
        val weeksBetween =
            ChronoUnit.WEEKS.between(dateNow, LocalDate.now())
        dataNow[1] = LocalDate.now().minusDays(LocalDate.now().dayOfWeek.value.toLong() - 1).toString()
        if (weeksBetween % 2 == 1.toLong()) {
            if (dataNow[0] == "1") dataNow[0] = "2"
            else dataNow[0] = "1"
        }
        return dataNow
    }


}