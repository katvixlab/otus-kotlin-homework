package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import helpers.fail
import models.DskError
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubValidationBadField(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации поля
    """.trimIndent()

    on { stubCase == DskStubs.BAD_FIELD && state == DskState.PROCESSING }
    handle {
        fail(
            DskError(
                group = "validation",
                code = "validation-field",
                field = "field",
                message = "Wrong field"
            )
        )
    }
}
