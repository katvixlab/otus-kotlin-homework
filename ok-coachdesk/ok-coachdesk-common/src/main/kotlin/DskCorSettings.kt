import ru.otus.kotlin.coachdesk.logging.common.DskLoggerProvider
import ru.otus.otuskotlin.marketplace.common.ws.IDskWsSessionRepo
import ws.IDskWsSession

class DskCorSettings(
    val loggerProvider: DskLoggerProvider = DskLoggerProvider(),
    val wsSession: IDskWsSessionRepo = IDskWsSessionRepo.NONE
) {

    companion object {
        val NONE = DskCorSettings()
    }
}