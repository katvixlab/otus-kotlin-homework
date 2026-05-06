package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import helpers.fail
import models.DskError
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker
import stubs.DskStubs

internal fun ICorChainDsl<DskContext>.stubErrorResponse(
    title: String,
    stubCase: DskStubs,
    code: String,
    message: String,
    field: String = "",
    group: String = "validation",
) = worker {
    this.title = title
    this.description = """
        Кейс ошибки стаба $stubCase
    """.trimIndent()
    on { this.stubCase == stubCase && state == DskState.PROCESSING }
    handle {
        fail(
            DskError(
                group = group,
                code = code,
                field = field,
                message = message,
            )
        )
    }
}
