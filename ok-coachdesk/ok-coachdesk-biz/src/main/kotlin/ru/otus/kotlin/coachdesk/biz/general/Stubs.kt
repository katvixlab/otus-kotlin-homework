package ru.otus.kotlin.coachdesk.biz.general

import DskContext
import models.DskState
import models.DskWorkMode
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.chain

fun ICorChainDsl<DskContext>.stubs(title: String, block: ICorChainDsl<DskContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == DskWorkMode.STUB && state == DskState.PROCESSING }
}
