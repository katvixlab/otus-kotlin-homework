package ok.coachdesk.app.plugins

import TrnRepoStub
import DskCorSettings
import TrnRepoInMemory
import io.ktor.server.application.Application
import ok.coachdesk.app.DskAppSettings
import ru.otus.kotlin.coachdesk.biz.DskProcessor

fun Application.initAppSettings(): DskAppSettings{
    val corSettings = DskCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoStub = TrnRepoStub(),
        repoTest = TrnRepoInMemory(),
        repoProd = TrnRepoInMemory(),
    )
    return DskAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: listOf(),
        corSettings = corSettings,
        processor = DskProcessor(corSettings)
    )
}