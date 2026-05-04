package ru.otus.kotlin.coachdesk.api.log.mapper

import DskContext
import kotlinx.datetime.Clock
import models.DskCommand
import models.DskError
import models.DskRequestId
import models.DskTrn
import models.DskTrnFilter
import ru.otus.kotlin.coachdesk.api.v1.models.*

fun DskContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ok-coachdesk",
    ts = toDskLogModel(),
    errors = errors.map { it.toLog() }
)

private fun DskContext.toDskLogModel(): DskTrnLogModel = DskTrnLogModel(
    requestId = requestId.takeIf { it != DskRequestId.NONE }?.asString(),
    operation = command.toLogOperation(),
    requestTrn = dskRequest.takeIf { it != DskTrn() }?.toLog(),
    requestFilter = dskTrnFilter.takeIf { it != DskTrnFilter() }?.toLog(),
    responseTrn = dskResponse.takeIf { it != DskTrn() }?.toLog(),
    responseTrns = dsksResponse.takeIf { it.isNotEmpty() }?.map { it.toLog() }
)

private fun DskCommand.toLogOperation(): TrnLogOperation = when (this) {
    DskCommand.CREATE -> TrnLogOperation.CREATE
    DskCommand.DELETE -> TrnLogOperation.DELETE
    DskCommand.READ -> TrnLogOperation.READ
    DskCommand.UPDATE -> TrnLogOperation.UPDATE
    DskCommand.SEARCH -> TrnLogOperation.SEARCH
    DskCommand.NONE -> TrnLogOperation.NONE
    DskCommand.INIT -> TrnLogOperation.INIT
    DskCommand.FINISH -> TrnLogOperation.FINISH
}

private fun DskTrn.toLog(): TrnLog = TrnLog(
    trnId = trnId.get(),
    coachId = coachId.get(),
    trnType = type.toString()
)

private fun DskTrnFilter.toLog(): TrnSearchFilterLog = TrnSearchFilterLog(
    clientFullName = clientFullName,
    startsAt = startsAt.toString(),
    type = type.toString(),
    status = status.toString(),
    paymentStatus = paymentStatus.toString()
)

private fun DskError.toLog() : ErrorLogModel = ErrorLogModel(
    message = message,
    field = field,
    code = code
)