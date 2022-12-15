package lab3

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


/*
near_lesson GROUP_NUMBER - ближайшее занятие для указанной группы;
DAY WEEK_NUMBER GROUP_NUMBER - расписание занятий в указанный день (monday, thuesday, ...).
Неделя может быть четной, нечетной;
tommorow GROUP_NUMBER - расписание на следующий день (если это воскресенье,
то выводится расписание на понедельник, учитывая, что неделя может быть четной или нечетной);
all WEEK_NUMBER GROUP_NUMBER - расписание на всю неделю.
*/

fun main() {
    val bot = TimetableBot(true)
    val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
    telegramBotsApi.registerBot(bot)
}