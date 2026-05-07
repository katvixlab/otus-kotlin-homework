package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import models.DskTrnPaymentStatus
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateFilterPaymentStatusNotEmpty(title: String) = worker {
    this.title = title
    on { trnFilterValidating.paymentStatus == DskTrnPaymentStatus.NONE }
    handle { fail(errorValidation("paymentStatus", "empty", "field must not be empty")) }
}
