import ru.otus.kotlin.coachdesk.logging.common.DskLoggerProvider

class DskCorSettings(
    val loggerProvider: DskLoggerProvider = DskLoggerProvider()
) {

    companion object {
        val NONE = DskCorSettings()
    }
}