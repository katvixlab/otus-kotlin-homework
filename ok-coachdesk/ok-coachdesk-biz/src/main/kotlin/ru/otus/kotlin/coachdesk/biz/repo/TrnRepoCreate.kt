package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import helpers.fail
import models.DskState
import repo.DbTrnRequest
import repo.DbTrnResponseErr
import repo.DbTrnResponseErrWithData
import repo.DbTrnResponseOk
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == DskState.PROCESSING }
    handle {
        val request = DbTrnRequest(trnRepoPrepare)
        when(val result = trnRepo.createTrn(request)) {
            is DbTrnResponseOk -> trnRepoDone = result.data
            is DbTrnResponseErr -> fail(result.errors)
            is DbTrnResponseErrWithData -> {
                fail(result.errors)
                trnRepoDone = result.data
            }
        }
    }
}