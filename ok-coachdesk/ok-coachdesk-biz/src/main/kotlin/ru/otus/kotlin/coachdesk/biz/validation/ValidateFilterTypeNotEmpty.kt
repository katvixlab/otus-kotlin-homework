package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import models.DskTrnType
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateFilterTypeNotEmpty(title: String) = worker {
    this.title = title
    on { trnFilterValidating.type == DskTrnType.NONE }
    handle { fail(errorValidation("type", "empty", "field must not be empty")) }
}
