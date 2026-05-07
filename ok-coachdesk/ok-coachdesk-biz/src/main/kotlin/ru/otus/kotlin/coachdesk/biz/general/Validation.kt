package ru.otus.kotlin.coachdesk.biz.general

import DskContext
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.chain

fun ICorChainDsl<DskContext>.validation(block: ICorChainDsl<DskContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == DskState.PROCESSING }
}
