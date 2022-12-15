package lab3

data class Schedule(
    val time: String,
    val typeSubject: String,
    val subject: String,
    val Name: String,
    val Audience: String,
    val week: String
) {
    /*override fun toString(): String {
        return "Время: $time\nПредмет: $subject($typeSubject): $Audience\nПреподаватель: $Name\n"
    }*/
    override fun toString(): String {
        return "$time\n$subject\n$typeSubject\n$Audience\n$Name\n$week"
    }
}
