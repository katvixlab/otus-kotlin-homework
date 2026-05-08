package ok.coachdesk.app.plugins

import DskCorSettings
import io.ktor.server.application.Application
import ok.coachdesk.app.DskAppSettings
import ru.otus.kotlin.coachdesk.biz.DskProcessor

fun Application.initAppSettings(): DskAppSettings{
    val corSettings = DskCorSettings(
        loggerProvider = getLoggerProviderConf()
    )
    return DskAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: listOf(),
        corSettings = corSettings,
        processor = DskProcessor(corSettings)
    )
}