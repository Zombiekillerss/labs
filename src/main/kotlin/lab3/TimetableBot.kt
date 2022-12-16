package lab3

import lab4.SQLDatabaseConnection
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

private val HANDLER = Handler()

private val OPENER = Opener()

private val SQL_DATABASE_CONNECTION = SQLDatabaseConnection()

private const val PATH = "C:\\Users\\sasha\\OneDrive\\Рабочий стол\\всякая всячина\\ООП\\3 курс\\"

private const val PATH_TO_WEEK = "${PATH}расписание\\неделя.txt"

private val TOKEN =
    OPENER.readDataFile("${PATH}BotData.txt")[1]
private val BOT_USER_NAME =
    OPENER.readDataFile("${PATH}BotData.txt")[0]


class TimetableBot(private val isLab3: Boolean) : TelegramLongPollingBot() {

    override fun getBotToken(): String = TOKEN

    override fun getBotUsername(): String = BOT_USER_NAME

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            val triple = getThreeValues(message)
            sendNotification(chatId, triple.third, triple.second, triple.first)
        }
    }

    private fun getThreeValues(
        message: Message,
    ): Triple<String, Boolean, String> {
        var responseText: String
        var group = ""
        var isGroup = true
        if (message.hasText()) {
            val messageText = message.text
            when (messageText) {
                "/start" -> responseText =
                    "Добро пожаловать!\nЯ буду показывать вам расписание на выбранный день и группу."

                "0301" -> {
                    isGroup = false
                    group = "0301"
                    responseText = "Выберите день"
                }

                "0302" -> {
                    isGroup = false
                    group = "0302"
                    responseText = "Выберите день"
                }

                "0303" -> {
                    isGroup = false
                    group = "0303"
                    responseText = "Выберите день"
                }

                "0304" -> {
                    isGroup = false
                    group = "0304"
                    responseText = "Выберите день"
                }

                "Назад" -> {
                    isGroup = true
                    responseText = "Выберите группу"
                }

                else -> responseText = "Не понимаю.\nНапишите что указано на кнопках или нажмите на них"
            }
            if (messageText.length > 4 && messageText[0] == '0') {
                isGroup = false
                group = messageText.substring(0, 4)
                responseText = getTimeTable(messageText.substring(messageText.indexOf(' ') + 1), group)
            }
        } else {
            responseText = "Я понимаю только текст"
        }
        return Triple(group, isGroup, responseText)
    }

    private fun getTimeTable(day: String, group: String): String {
        return if (isLab3)
            HANDLER.getStructureInformationFromFile("${PATH}расписание\\${group}.txt", PATH_TO_WEEK, OPENER, day)
        else
            HANDLER.getStructureInformationFromSQL(SQL_DATABASE_CONNECTION, day, group)
    }

    private fun sendNotification(chatId: Long, responseText: String, isGroup: Boolean, group: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.enableMarkdown(true)

        responseMessage.replyMarkup = if (isGroup) getReplyMarkup(
            listOf(
                listOf("0301"),
                listOf("0302"),
                listOf("0303"),
                listOf("0304"),
            )
        )
        else
            getReplyMarkup(
                listOf(
                    listOf("$group Понедельник"),
                    listOf("$group Вторник"),
                    listOf("$group Среда"),
                    listOf("$group Четверг"),
                    listOf("$group Пятница"),
                    listOf("$group Суббота"),
                    listOf("Назад")
                )
            )
        execute(responseMessage)
    }

    private fun getReplyMarkup(allButtons: List<List<String>>): ReplyKeyboardMarkup {
        val markup = ReplyKeyboardMarkup()
        markup.keyboard = allButtons.map { rowButtons ->
            val row = KeyboardRow()
            rowButtons.forEach { rowButton -> row.add(rowButton) }
            row
        }

        return markup
    }
}