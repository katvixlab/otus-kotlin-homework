package ru.otus.kotlin.coachdesk.biz.general

import DskContext
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.initStatus(title: String) = worker() {
    this.title = title
    this.description = """
        Этот обработчик устанавливает стартовый статус обработки. Запускается только в случае не заданного статуса.
    """.trimIndent()
    on { state == DskState.NONE }
    handle { state = DskState.PROCESSING }
}
