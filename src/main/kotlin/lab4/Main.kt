package lab4

fun main() {
    /*val bot = TimetableBot(false)
    val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
    telegramBotsApi.registerBot(bot)*/
    print(SQLDatabaseConnection().getInformation("0302", "2", "Понедельник")[0])
}