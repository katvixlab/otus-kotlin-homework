package ru.otus.kotlin.coachdesk.biz.general

import DskContext
import models.DskCommand
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.chain

fun ICorChainDsl<DskContext>.operation(
    title: String,
    command: DskCommand,
    block: ICorChainDsl<DskContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == DskState.PROCESSING }
}
