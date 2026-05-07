package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import models.DskTrnId
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateTrnIdNotEmpty(title: String) = worker {
    this.title = title
    on { trnValidating.trnId.get() == DskTrnId.NONE.get() }
    handle { fail(errorValidation("trnId", "empty", "field must not be empty")) }
}
