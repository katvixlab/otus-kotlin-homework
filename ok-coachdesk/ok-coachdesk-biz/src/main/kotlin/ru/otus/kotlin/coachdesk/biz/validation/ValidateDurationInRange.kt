package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

fun ICorChainDsl<DskContext>.validateDurationInRange(title: String) = worker {
    this.title = title
    on { trnValidating.durationMin < 10.minutes || trnValidating.durationMin > 8.hours }
    handle { fail(errorValidation("durationMin", "range", "field must be from 10 minutes to 8 hours")) }
}
