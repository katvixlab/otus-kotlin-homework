import kotlinx.datetime.Instant
import models.*
import ru.otus.kotlin.coachdesk.api.v1.models.*
import stubs.DskStubs
import java.lang.Integer.parseInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes


fun DskContext.fromTransport(request: IRequest) = when (request) {
    is TrnCreateRequest -> from(request)
    is TrnDeleteRequest -> from(request)
    is TrnReadRequest -> from(request)
    is TrnSearchRequest -> from(request)
    is TrnUpdateRequest -> from(request)
    else -> throw IllegalArgumentException("Unsupported request $request")
}

fun DskContext.from(request: TrnCreateRequest) {
    command = DskCommand.CREATE
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnRequest = request.trn?.toInternal() ?: DskTrn()

}

fun DskContext.from(request: TrnDeleteRequest) {
    command = DskCommand.DELETE
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnRequest = request.trn?.toInternal() ?: DskTrn()
}

fun DskContext.from(request: TrnReadRequest) {
    command = DskCommand.READ
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnRequest = request.trn?.toInternal() ?: DskTrn()
}

fun DskContext.from(request: TrnSearchRequest) {
    command = DskCommand.SEARCH
}

fun DskContext.from(request: TrnUpdateRequest) {
    command = DskCommand.UPDATE
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnRequest = request.trn?.toInternal() ?: DskTrn()
}

private fun TrnDebug?.toContextWorkMode() = when (this?.mode) {
    TrnRequestDebugMode.PROD -> DskWorkMode.PROD
    TrnRequestDebugMode.TEST -> DskWorkMode.TEST
    TrnRequestDebugMode.STUB -> DskWorkMode.STUB
    null -> DskWorkMode.PROD
}

private fun TrnDebug?.toContextStubCase() = when (this?.stub) {
    TrnRequestDebugStubs.CANNOT_DELETE -> DskStubs.CANNOT_DELETE
    TrnRequestDebugStubs.NOT_FOUND -> DskStubs.NOT_FOUND
    TrnRequestDebugStubs.MISMATCH_SEARCH_STRING -> DskStubs.MISMATCH_SEARCH_STRING
    TrnRequestDebugStubs.DB_ERROR -> DskStubs.DB_ERROR
    TrnRequestDebugStubs.BAD_ID -> DskStubs.BAD_ID
    TrnRequestDebugStubs.BAD_FIELD -> DskStubs.BAD_FIELD
    TrnRequestDebugStubs.BAD_COACH_ID -> DskStubs.BAD_COACH_ID
    TrnRequestDebugStubs.BAD_CLIENT_ID -> DskStubs.BAD_CLIENT_ID
    TrnRequestDebugStubs.BAD_STARTS_AT -> DskStubs.BAD_STARTS_AT
    TrnRequestDebugStubs.SUCCESS -> DskStubs.SUCCESS
    null -> DskStubs.NONE
}

private fun TrnRequestObject.toInternal(): DskTrn = DskTrn(
    trnId = this.trnId?.let { DskTrnId(it) } ?: DskTrnId.NONE,
    coachId = this.coachId?.let { DskCoachId(it) } ?: DskCoachId.NONE,
    clientFullName = this.clientFullName ?: "",
    startsAt = this.startsAt?.let { Instant.parse(it) } ?: Instant.NONE,
    durationMin = this.durationMin?.let { parseInt(it) }?.minutes ?: Duration.ZERO,
    type = this.type?.toInternal() ?: DskTrnType.NONE,
    planNotes = this.planNotes ?: "",
    resultNotes = this.resultNotes ?: "",
    status = this.status?.toInternal() ?: DskTrnStatus.NONE,
    paymentStatus = this.paymentStatus?.toInternal() ?: DskTrnPaymentStatus.NONE
)

private fun TrnType.toInternal(): DskTrnType = when (this) {
    TrnType.PERSONAL -> DskTrnType.PERSONAL
    TrnType.FUNCTIONAL -> DskTrnType.FUNCTIONAL
    TrnType.STRENGTH -> DskTrnType.STRENGTH
    TrnType.CARDIO -> DskTrnType.CARDIO
    TrnType.CROSS_FIT -> DskTrnType.CROSS_FIT
    TrnType.OTHER -> DskTrnType.OTHER
}

private fun TrnStatus.toInternal(): DskTrnStatus = when (this) {
    TrnStatus.CANCELED -> DskTrnStatus.CANCELED
    TrnStatus.DONE -> DskTrnStatus.DONE
    TrnStatus.PLANNED -> DskTrnStatus.PLANNED
}

private fun TrnPaymentStatus.toInternal(): DskTrnPaymentStatus = when (this) {
    TrnPaymentStatus.PAID -> DskTrnPaymentStatus.PAID
    TrnPaymentStatus.UNPAID -> DskTrnPaymentStatus.UNPAID
}

private fun IdTrn.toInternal(): DskTrn = DskTrn(
    trnId = this.trnId?.let { DskTrnId(it) } ?: DskTrnId.NONE,
    coachId = this.coachId?.let { DskCoachId(it) } ?: DskCoachId.NONE,
)
