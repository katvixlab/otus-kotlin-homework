package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import helpers.errorValidation
import helpers.fail
import models.DskCoachId
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.validateCoachIdNotEmpty(title: String) = worker {
    this.title = title
    on { trnValidating.coachId == DskCoachId.NONE }
    handle { fail(errorValidation("coachId", "empty", "field must not be empty")) }
}
