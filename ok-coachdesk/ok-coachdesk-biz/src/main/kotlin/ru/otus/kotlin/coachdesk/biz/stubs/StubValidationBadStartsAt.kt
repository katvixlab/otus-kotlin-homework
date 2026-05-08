package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubValidationBadStartsAt(title: String) = stubErrorResponse(
    title = title,
    stubCase = DskStubs.BAD_STARTS_AT,
    field = "startsAt",
    code = "validation-starts-at",
    message = "Wrong training start time",
)
