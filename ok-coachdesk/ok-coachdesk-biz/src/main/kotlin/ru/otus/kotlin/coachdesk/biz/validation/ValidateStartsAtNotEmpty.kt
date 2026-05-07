package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import NONE
import helpers.errorValidation
import helpers.fail
import kotlinx.datetime.Instant
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateStartsAtNotEmpty(title: String) = worker {
    this.title = title
    on { trnValidating.startsAt == Instant.NONE }
    handle { fail(errorValidation("startsAt", "empty", "field must not be empty")) }
}
