package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import models.DskClientId
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateClientIdNotEmpty(title: String) = worker {
    this.title = title
    on { trnValidating.clientId == DskClientId.NONE }
    handle { fail(errorValidation("clientId", "empty", "field must not be empty")) }
}
