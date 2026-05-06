package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubValidationBadClientId(title: String) = stubErrorResponse(
    title = title,
    stubCase = DskStubs.BAD_CLIENT_ID,
    field = "clientId",
    code = "validation-client-id",
    message = "Wrong client id",
)
