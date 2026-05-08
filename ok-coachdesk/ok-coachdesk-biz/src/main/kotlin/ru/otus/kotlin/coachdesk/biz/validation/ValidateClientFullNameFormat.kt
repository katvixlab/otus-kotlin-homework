package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateClientFullNameFormat(title: String) = worker {
    this.title = title
    on {
        val nameParts = trnValidating.clientFullName.split(Regex("\\s+"))
        nameParts.size !in 2..3 || nameParts.any { it.length !in 2..20 }
    }
    handle {
        fail(
            errorValidation(
                field = "clientFullName",
                violationCode = "format",
                description = "field must contain first and last names, optional middle name; each word length must be from 2 to 20"
            )
        )
    }
}
