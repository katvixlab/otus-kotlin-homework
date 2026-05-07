package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validatePlanNotesHasText(title: String) = worker {
    this.title = title
    on { trnValidating.planNotes.isNotBlank() && trnValidating.planNotes.none { it.isLetterOrDigit() } }
    handle { fail(errorValidation("planNotes", "no-text", "field must be empty or contain text")) }
}
