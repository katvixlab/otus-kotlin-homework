package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubMismatchSearchString(title: String) = stubErrorResponse(
    title = title,
    stubCase = DskStubs.MISMATCH_SEARCH_STRING,
    field = "clientFullName",
    code = "validation-search-string",
    message = "Wrong search string",
)
