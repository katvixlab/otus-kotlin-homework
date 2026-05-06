package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import DskCorSettings
import DskStub
import NONE
import kotlinx.datetime.Instant
import models.DskCoachId
import models.DskClientId
import models.DskState
import models.DskTrnId
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker
import ru.otus.kotlin.coachdesk.logging.common.LogLevel
import stubs.DskStubs

internal fun ICorChainDsl<DskContext>.stubSuccessResponse(title: String, corSettings: DskCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для обработки тренировки
    """.trimIndent()
    on { stubCase == DskStubs.SUCCESS && state == DskState.PROCESSING }
    val logger = corSettings.loggerProvider.logger("stubSuccess")
    handle {
        logger.doWithLogging(id = requestId.asString(), LogLevel.DEBUG) {
            state = DskState.FINISHED
            dskResponse = DskStub.prepareResult {
                dskRequest.trnId.takeIf { it.get() != DskTrnId.NONE.get() }?.also { trnId = it }
                dskRequest.coachId.takeIf { it != DskCoachId.NONE }?.also { coachId = it }
                dskRequest.clientId.takeIf { it != DskClientId.NONE }?.also { clientId = it }
                dskRequest.clientFullName.takeIf { it.isNotBlank() }?.also { clientFullName = it }
                dskRequest.startsAt.takeIf { it != Instant.NONE }?.also { startsAt = it }
                dskRequest.durationMin.takeIf { it.isPositive() }?.also { durationMin = it }
                dskRequest.type.takeIf { it != DskTrnType.NONE }?.also { type = it }
                dskRequest.planNotes.takeIf { it.isNotBlank() }?.also { planNotes = it }
                dskRequest.resultNotes.takeIf { it.isNotBlank() }?.also { resultNotes = it }
                dskRequest.status.takeIf { it != DskTrnStatus.NONE }?.also { status = it }
                dskRequest.paymentStatus.takeIf { it != DskTrnPaymentStatus.NONE }?.also { paymentStatus = it }
            }
        }
    }
}
