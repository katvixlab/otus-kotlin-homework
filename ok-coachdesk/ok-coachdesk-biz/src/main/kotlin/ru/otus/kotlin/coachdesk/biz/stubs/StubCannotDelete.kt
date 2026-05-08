package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubCannotDelete(title: String) = stubErrorResponse(
    title = title,
    stubCase = DskStubs.CANNOT_DELETE,
    field = "trnId",
    code = "cannot-delete",
    message = "Cannot delete training",
)
