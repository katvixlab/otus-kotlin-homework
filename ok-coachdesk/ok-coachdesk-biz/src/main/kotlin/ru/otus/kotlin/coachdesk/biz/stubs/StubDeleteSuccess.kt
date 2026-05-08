package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import DskCorSettings
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl

fun ICorChainDsl<DskContext>.stubDeleteSuccess(title: String, corSettings: DskCorSettings) =
    stubSuccessResponse(title, corSettings)
