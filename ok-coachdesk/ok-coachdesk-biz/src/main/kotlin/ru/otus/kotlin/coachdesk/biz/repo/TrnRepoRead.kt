package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import helpers.fail
import models.DskState
import repo.DbTrnIdRequest
import repo.DbTrnResponseErr
import repo.DbTrnResponseErrWithData
import repo.DbTrnResponseOk
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == DskState.PROCESSING }
    handle {
        val request = DbTrnIdRequest(trnValidated)
        when (val result = trnRepo.readTrn(request)) {
            is DbTrnResponseOk -> trnRepoRead = result.data
            is DbTrnResponseErr -> fail(result.errors)
            is DbTrnResponseErrWithData -> {
                fail(result.errors)
                trnRepoRead = result.data
            }
        }
    }
}
