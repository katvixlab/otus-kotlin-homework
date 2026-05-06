package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubNotFound(title: String) = stubErrorResponse(
    title = title,
    stubCase = DskStubs.NOT_FOUND,
    group = "not-found",
    field = "trnId",
    code = "not-found",
    message = "Training not found",
)
