package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = "Готовим данные к удалению из БД"
    on { state == DskState.PROCESSING }
    handle {
        trnRepoPrepare = trnValidated.copy()
    }
}
