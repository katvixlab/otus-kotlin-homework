package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubValidationBadId(title: String) = stubErrorResponse(
    title = title,
    stubCase = DskStubs.BAD_ID,
    field = "trnId",
    code = "validation-id",
    message = "Wrong training id",
)
