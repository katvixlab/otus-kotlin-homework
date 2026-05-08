package ru.otus.kotlin.coachdesk.biz.stubs

import DskContext
import DskCorSettings
import ru.otus.kotlin.coachdesk.cor.ICorChainDsl

fun ICorChainDsl<DskContext>.stubReadSuccess(title: String, corSettings: DskCorSettings) =
    stubSuccessResponse(title, corSettings)
