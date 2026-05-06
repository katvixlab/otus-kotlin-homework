package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import helpers.fail
import models.DskError
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker
import stubs.DskStubs

fun ICorChainDsl<DskContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == DskStubs.DB_ERROR && state == DskState.PROCESSING }
    handle {
        fail(
            DskError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
