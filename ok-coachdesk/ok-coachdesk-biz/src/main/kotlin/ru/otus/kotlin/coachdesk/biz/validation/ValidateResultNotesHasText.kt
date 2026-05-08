package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateResultNotesHasText(title: String) = worker {
    this.title = title
    on { trnValidating.resultNotes.isNotBlank() && trnValidating.resultNotes.none { it.isLetterOrDigit() } }
    handle { fail(errorValidation("resultNotes", "no-text", "field must be empty or contain text")) }
}
