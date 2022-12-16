package lab4

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

private const val CONNECTION_URL =
    "jdbc:sqlserver://LAPTOP-HB8D0KE8\\SQLEXPRESS;encrypt=true;database=TimeTable;trustServerCertificate=true;"

class SQLDatabaseConnection {

    fun getInformation(group: String, parityWeek: String, day: String): List<String> {
        return try {
            val connection = DriverManager.getConnection(CONNECTION_URL, "bot", "1")
            val result = extracted(connection, group, parityWeek, day)
            connection.close()
            result.split("\n")
        } catch (e: SQLException) {
            e.printStackTrace()
            listOf()
        }
    }

    fun getInformationParity(): MutableList<String> {
        try {
            val connection = DriverManager.getConnection(CONNECTION_URL, "bot", "1")
            val state =
                connection.prepareStatement(
                    "select * from Week"
                )
            val line = state.executeQuery()
            line.next()
            val result = line.getString("WeekNow").trim() + "\n" +
                    line.getString("DayWeek").trim()
            connection.close()
            return result.split("\n").toMutableList()
        } catch (e: SQLException) {
            e.printStackTrace()
            return mutableListOf()
        }
    }

    fun updateParity(newDayWeek: String, newParity: String) {
        try {
            val connection = DriverManager.getConnection(CONNECTION_URL, "bot", "1")
            val state = connection.prepareStatement(
                "update Week\n" +
                        "set DayWeek='$newDayWeek'" +
                        ", WeekNow='$newParity'"
            )
            state.executeUpdate()
            connection.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun extracted(
        connection: Connection, group: String, parityWeek: String, day: String
    ): String {
        val state =
            connection.prepareStatement(
                "select * from TimeTableOnDay where NumberGroup='${group}' " +
                        "and DayVariant='${day}' and WeekSubjects='${parityWeek[0]}'"
            )
        val line = state.executeQuery()
        var result = ""
        while (line.next()) {
            result += line.getString("WeekSubjects").trim() + ":" +
                    line.getString("TimeSubject").trim() + ":" +
                    line.getString("TypeSubject").trim() + ":" +
                    line.getString("SubjectCurrent").trim() + ":" +
                    line.getString("NameSubject").trim() + ":" +
                    line.getString("Audiencecurrent").trim() + "\n"
        }
        if (result.isNotEmpty())
            result = result.substring(0, result.length - 1)
        return result
    }
}