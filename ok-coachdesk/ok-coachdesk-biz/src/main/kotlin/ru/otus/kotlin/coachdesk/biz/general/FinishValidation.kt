package ru.otus.kotlin.coachdesk.biz.general

import DskContext
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.finishTrnValidation(title: String) = worker {
    this.title = title
    on { state == DskState.PROCESSING }
    handle {
        trnValidated = trnValidating
    }
}

fun ICorChainDsl<DskContext>.finishTrnFilterValidation(title: String) = worker {
    this.title = title
    on { state == DskState.PROCESSING }
    handle {
        trnFilterValidated = trnFilterValidating
    }
}
