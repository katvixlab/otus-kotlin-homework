package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateFilterClientFullNameNotEmpty(title: String) = worker {
    this.title = title
    on { trnFilterValidating.clientFullName.isBlank() }
    handle { fail(errorValidation("clientFullName", "empty", "field must not be empty")) }
}
