package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import helpers.fail
import models.DskState
import repo.errorRepoConcurrency
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.checkLock(title: String) = worker {
    this.title = title
    description = """
        Проверка оптимистичной блокировки. Если не равна сохраненной в БД, значит данные запроса устарели 
        и необходимо их обновить вручную
    """.trimIndent()
    on { state == DskState.PROCESSING && trnValidated.lock != trnRepoRead.lock }
    handle {
        fail(errorRepoConcurrency(trnRepoRead, trnValidated.lock).errors)
    }
}