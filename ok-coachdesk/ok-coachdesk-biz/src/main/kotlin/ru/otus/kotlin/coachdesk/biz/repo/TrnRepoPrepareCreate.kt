package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == DskState.PROCESSING }
    handle {
        trnRepoPrepare = trnValidated.copy()
    }
}
