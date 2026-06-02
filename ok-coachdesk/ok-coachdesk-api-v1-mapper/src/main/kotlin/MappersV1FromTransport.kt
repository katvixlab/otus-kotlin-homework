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
    trnRequest = request.toInternal()

}

fun DskContext.from(request: TrnDeleteRequest) {
    command = DskCommand.DELETE
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnRequest = request.toInternal()
}

fun DskContext.from(request: TrnReadRequest) {
    command = DskCommand.READ
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnRequest = request.toInternal()
}

fun DskContext.from(request: TrnSearchRequest) {
    command = DskCommand.SEARCH
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnFilterRequest = request.toInternal()
}

fun DskContext.from(request: TrnUpdateRequest) {
    command = DskCommand.UPDATE
    workMode = request.debug.toContextWorkMode()
    stubCase = request.debug.toContextStubCase()
    trnRequest = request.toInternal()
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

private fun TrnCreateRequest.toInternal(): DskTrn = DskTrn(
    trnId = DskTrnId.NONE,
    coachId = this.trn?.coachId?.let { DskCoachId(it) } ?: DskCoachId.NONE,
    clientId = this.trn?.clientId?.let { DskClientId(it) } ?: DskClientId.NONE,
    clientFullName = this.trn?.clientFullName ?: "",
    startsAt = this.trn?.startsAt?.let { Instant.parse(it) } ?: Instant.NONE,
    durationMin = this.trn?.durationMin?.let { parseInt(it) }?.minutes ?: Duration.ZERO,
    type = this.trn?.type?.toInternal() ?: DskTrnType.NONE,
    planNotes = this.trn?.planNotes ?: "",
    resultNotes = this.trn?.resultNotes ?: "",
    status = this.trn?.status?.toInternal() ?: DskTrnStatus.NONE,
    paymentStatus = this.trn?.paymentStatus?.toInternal() ?: DskTrnPaymentStatus.NONE,
)

private fun TrnUpdateRequest.toInternal(): DskTrn = DskTrn(
    trnId = this.trn?.trnId?.let { DskTrnId(it) } ?: DskTrnId.NONE,
    coachId = this.trn?.coachId?.let { DskCoachId(it) } ?: DskCoachId.NONE,
    clientId = this.trn?.clientId?.let { DskClientId(it) } ?: DskClientId.NONE,
    clientFullName = this.trn?.clientFullName ?: "",
    startsAt = this.trn?.startsAt?.let { Instant.parse(it) } ?: Instant.NONE,
    durationMin = this.trn?.durationMin?.let { parseInt(it) }?.minutes ?: Duration.ZERO,
    type = this.trn?.type?.toInternal() ?: DskTrnType.NONE,
    planNotes = this.trn?.planNotes ?: "",
    resultNotes = this.trn?.resultNotes ?: "",
    status = this.trn?.status?.toInternal() ?: DskTrnStatus.NONE,
    paymentStatus = this.trn?.paymentStatus?.toInternal() ?: DskTrnPaymentStatus.NONE,
    lock = this.lock?.let { DskTrnLock(it) } ?: DskTrnLock.NONE,
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

private fun TrnDeleteRequest.toInternal(): DskTrn = DskTrn(
    trnId = this.trn?.trnId?.let { DskTrnId(it) } ?: DskTrnId.NONE,
    lock = this.lock?.let { DskTrnLock(it) } ?: DskTrnLock.NONE,
)

private fun TrnReadRequest.toInternal(): DskTrn = DskTrn(
    trnId = this.trn?.trnId?.let { DskTrnId(it) } ?: DskTrnId.NONE,
)

private fun TrnSearchRequest.toInternal(): DskTrnFilter = DskTrnFilter(
    clientFullName = this.trnFilter?.clientFullName ?: "",
    startsAt = this.trnFilter?.startsAt?.let { Instant.parse(it) } ?: Instant.NONE,
    type = this.trnFilter?.type?.toInternal() ?: DskTrnType.NONE,
    status = this.trnFilter?.status?.toInternal() ?: DskTrnStatus.NONE,
    paymentStatus = this.trnFilter?.paymentStatus?.toInternal() ?: DskTrnPaymentStatus.NONE,
)
