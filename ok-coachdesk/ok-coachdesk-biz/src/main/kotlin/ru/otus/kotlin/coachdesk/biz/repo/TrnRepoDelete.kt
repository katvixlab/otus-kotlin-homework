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

fun ICorChainDsl<DskContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == DskState.PROCESSING }
    handle {
        val request = DbTrnIdRequest(trnRepoPrepare)
        when (val result = trnRepo.deleteTrn(request)) {
            is DbTrnResponseOk -> trnRepoDone = result.data
            is DbTrnResponseErr -> {
                fail(result.errors)
                trnRepoDone = trnRepoRead
            }

            is DbTrnResponseErrWithData -> {
                fail(result.errors)
                trnRepoDone = result.data
            }
        }
    }
}
