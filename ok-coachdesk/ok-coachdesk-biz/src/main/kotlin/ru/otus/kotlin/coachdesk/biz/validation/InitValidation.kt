package ru.otus.kotlin.coachdesk.biz.validation

import DskContext
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.initTrnValidation(title: String) = worker {
    this.title = title

    handle {
        trnValidating = trnRequest.copy()
    }
}

fun ICorChainDsl<DskContext>.initTrnFilterValidation(title: String) = worker {
    this.title = title

    handle {
        trnFilterValidating = trnFilterRequest.copy()
    }
}
