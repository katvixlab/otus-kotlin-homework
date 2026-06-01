package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import NONE
import helpers.errorValidation
import helpers.fail
import kotlinx.datetime.Instant
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateFilterNotEmpty(title: String) = worker {
    this.title = title
    on {
        trnFilterValidating.clientFullName.isBlank() &&
                trnFilterValidating.startsAt == Instant.NONE &&
                trnFilterValidating.type == DskTrnType.NONE &&
                trnFilterValidating.status == DskTrnStatus.NONE &&
                trnFilterValidating.paymentStatus == DskTrnPaymentStatus.NONE
    }
    handle { fail(errorValidation("all", "empty", "one field must not be empty")) }
}
