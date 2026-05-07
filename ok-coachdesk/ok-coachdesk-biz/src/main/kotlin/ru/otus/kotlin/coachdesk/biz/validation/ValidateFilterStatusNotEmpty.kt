package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import models.DskTrnStatus
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateFilterStatusNotEmpty(title: String) = worker {
    this.title = title
    on { trnFilterValidating.status == DskTrnStatus.NONE }
    handle { fail(errorValidation("status", "empty", "field must not be empty")) }
}
