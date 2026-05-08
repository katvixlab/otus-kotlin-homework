package ok.coachdesk.app.plugins

import io.ktor.server.application.Application
import ru.otus.kotlin.coachdesk.logging.common.DskLoggerProvider
import ru.otus.kotlin.coachdesk.logging.jvm.dskLoggerLogback

fun Application.getLoggerProviderConf(): DskLoggerProvider =
    when(val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> DskLoggerProvider { dskLoggerLogback(it) }
        else -> IllegalArgumentException("Logger $mode is not allowed.")
    } as DskLoggerProvider