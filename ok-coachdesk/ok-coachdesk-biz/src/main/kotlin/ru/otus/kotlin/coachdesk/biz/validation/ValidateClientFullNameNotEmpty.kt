package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateClientFullNameNotEmpty(title: String) = worker {
    this.title = title
    on { trnValidating.clientFullName.isBlank() }
    handle {
        fail(
            errorValidation(
                field = "clientFullName",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
