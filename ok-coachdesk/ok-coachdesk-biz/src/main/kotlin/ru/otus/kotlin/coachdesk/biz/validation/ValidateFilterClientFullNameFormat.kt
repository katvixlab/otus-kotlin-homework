package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

private val clientFullNameFilterRegex = Regex("""^[\p{L}][\p{L} -]{1,59}$""")

fun ICorChainDsl<DskContext>.validateFilterClientFullNameFormat(title: String) = worker {
    this.title = title
    on {
        trnFilterValidating.clientFullName.isNotBlank() &&
                !trnFilterValidating.clientFullName.matches(clientFullNameFilterRegex)
    }
    handle {
        fail(
            errorValidation(
                field = "clientFullName",
                violationCode = "format",
                description = "field must contain only letters, spaces or hyphen; length must be from 2 to 60"
            )
        )
    }
}
