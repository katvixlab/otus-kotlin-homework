package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubValidationBadCoachId(title: String) = stubErrorResponse(
    title = title,
    stubCase = DskStubs.BAD_COACH_ID,
    field = "coachId",
    code = "validation-coach-id",
    message = "Wrong coach id",
)
