package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import models.DskState
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description =
        "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, и данные, полученные от пользователя"
    on { state == DskState.PROCESSING }
    handle {
        trnRepoPrepare = trnRepoRead.copy().apply {
            startsAt = trnValidated.startsAt
            durationMin = trnValidated.durationMin
            type = trnValidated.type
            planNotes = trnValidated.planNotes
            resultNotes = trnValidated.resultNotes
            status = trnValidated.status
            paymentStatus = trnValidated.paymentStatus
        }
    }
}
