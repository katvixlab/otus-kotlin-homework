package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import NONE
import helpers.errorValidation
import helpers.fail
import kotlinx.datetime.Instant
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateStartsAtNotPast(title: String) = worker {
    this.title = title
    on {
        timeStart != Instant.NONE &&
            trnValidating.startsAt != Instant.NONE &&
            trnValidating.startsAt < timeStart
    }
    handle { fail(errorValidation("startsAt", "past", "field must not be in the past")) }
}
