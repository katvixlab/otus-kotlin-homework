package ru.otus.kotlin.coachdesk.biz

import DskContext
import DskCorSettings
import DskStub
import models.DskState

class DskProcessor(val corSettings: DskCorSettings) {

    suspend fun exec(ctx: DskContext) {
        ctx.dskResponse = DskStub.get()
        ctx.dsksResponse = DskStub.prepareListDskTrn("tests").toMutableList()
        ctx.state = DskState.PROCESSING
    }
}