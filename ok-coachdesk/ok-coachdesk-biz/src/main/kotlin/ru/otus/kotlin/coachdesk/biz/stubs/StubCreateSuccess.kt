package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import DskCorSettings
import DskStub
import NONE
import kotlinx.datetime.Instant
import models.DskCoachId
import models.DskClientId
import models.DskState
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker
import ru.otus.kotlin.coachdesk.logging.common.LogLevel
import stubs.DskStubs
import kotlin.time.Duration

fun ICorChainDsl<DskContext>.stubCreateSuccess(title: String, corSettings: DskCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания тренировки
    """.trimIndent()
    on { stubCase == DskStubs.SUCCESS && state == DskState.PROCESSING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = DskState.FINISHED
            val stub = DskStub.prepareResult {
                dskRequest.clientFullName.takeIf { it.isNotBlank() }?.also { this.clientFullName = it }
                dskRequest.coachId.takeIf { it != DskCoachId.NONE }?.also { this.coachId = it }
                dskRequest.clientId.takeIf { it != DskClientId.NONE }?.also { this.clientId = it }
                dskRequest.startsAt.takeIf { it != Instant.NONE }?.also { this.startsAt = it }
                dskRequest.paymentStatus.takeIf { it != DskTrnPaymentStatus.NONE }?.also { this.paymentStatus = it }
                dskRequest.durationMin.takeIf { it != Duration.ZERO }?.also { this.durationMin = it }
                dskRequest.type.takeIf { it != DskTrnType.NONE }?.also { this.type = it }
                dskRequest.planNotes.takeIf { it.isNotBlank() }?.also { this.planNotes = it }
                dskRequest.resultNotes.takeIf { it.isNotBlank() }?.also { this.resultNotes = it }
                dskRequest.status.takeIf { it != DskTrnStatus.NONE }?.also { this.status = it }
            }
            dskResponse = stub
        }
    }
}
