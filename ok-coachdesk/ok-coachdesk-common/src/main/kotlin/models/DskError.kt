package models

import ru.otus.kotlin.coachdesk.logging.common.LogLevel


data class DskError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)
