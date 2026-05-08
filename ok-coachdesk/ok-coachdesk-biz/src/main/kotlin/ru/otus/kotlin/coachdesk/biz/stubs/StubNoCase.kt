package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import helpers.fail
import models.DskError
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки для неподдерживаемого стаба
    """.trimIndent()
    on { state == DskState.PROCESSING }
    handle {
        fail(
            DskError(
                group = "stub",
                code = "unsupported-stub",
                field = "stub",
                message = "Unsupported stub case: $stubCase"
            )
        )
    }
}
