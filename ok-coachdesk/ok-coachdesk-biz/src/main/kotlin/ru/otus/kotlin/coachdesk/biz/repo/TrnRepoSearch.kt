package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import helpers.fail
import models.DskState
import repo.DbTrnFilterRequest
import repo.DbTrnsResponseErr
import repo.DbTrnsResponseOk
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == DskState.PROCESSING }
    handle {
        val request = DbTrnFilterRequest(
            clientFullName = trnFilterValidated.clientFullName,
            startsAt = trnFilterValidated.startsAt,
            type = trnFilterValidated.type,
            status = trnFilterValidated.status,
            paymentStatus = trnFilterValidated.paymentStatus,
        )
        when (val result = trnRepo.searchTrn(request)) {
            is DbTrnsResponseOk -> trnsRepoDone = result.data.toMutableList()
            is DbTrnsResponseErr -> fail(result.errors)
        }
    }
}
