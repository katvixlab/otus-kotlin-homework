package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import DskCorSettings
import DskStub
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker
import ru.otus.kotlin.coachdesk.logging.common.LogLevel
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubSearchSuccess(title: String, corSettings: DskCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска тренировок
    """.trimIndent()
    on { stubCase == DskStubs.SUCCESS && state == DskState.PROCESSING }
    val logger = corSettings.loggerProvider.logger("stubSearchSuccess")
    handle {
        logger.doWithLogging(id = requestId.asString(), LogLevel.DEBUG) {
            state = DskState.FINISHED
            trnsResponse = DskStub.prepareListDskTrn().toMutableList()
        }
    }
}
