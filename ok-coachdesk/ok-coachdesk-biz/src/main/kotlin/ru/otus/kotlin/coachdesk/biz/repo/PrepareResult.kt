package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import models.DskState
import models.DskWorkMode
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != DskWorkMode.STUB }
    handle {
        trnResponse = trnRepoDone
        trnsResponse = trnsRepoDone
        state = when (val st = state) {
            DskState.PROCESSING -> DskState.FINISHED
            else -> st
        }
    }
}
