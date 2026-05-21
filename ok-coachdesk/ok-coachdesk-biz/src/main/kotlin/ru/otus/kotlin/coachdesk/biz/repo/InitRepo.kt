package ru.otus.kotlin.coachdesk.biz.repo

import DskContext
import models.DskWorkMode
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl
import ru.otus.kotlin.coachdesk.cor.worker

fun ICorChainDsl<DskContext>.initRepo(title: String) = worker {
    this.title = title
    description = "Выбор рабочего репозитория в зависимости от запрошенного режима работы"
    handle {
        trnRepo = when (workMode) {
            DskWorkMode.TEST -> corSettings.repoTest
            DskWorkMode.STUB -> corSettings.repoStub
            DskWorkMode.PROD -> corSettings.repoProd
        }
    }
}