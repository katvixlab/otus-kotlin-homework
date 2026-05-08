import DskContext
import kotlinx.datetime.Instant
import models.DskCommand
import models.DskError
import models.DskState
import models.DskTrn
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import ru.otus.kotlin.coachdesk.api.v1.models.Error
import ru.otus.kotlin.coachdesk.api.v1.models.IResponse
import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnInitResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnPaymentStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnResponseObject
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnType
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateResponse
import java.util.UUID
import kotlin.time.Duration

fun DskContext.toTransport(): IResponse = when (command) {
    DskCommand.CREATE -> toTransportCreate()
    DskCommand.UPDATE -> toTransportUpdate()
    DskCommand.READ -> toTransportRead()
    DskCommand.SEARCH -> toTransportSearch()
    DskCommand.DELETE -> toTransportDelete()
    DskCommand.INIT -> toTransportInit()
    DskCommand.FINISH -> object : IResponse {
        override val responseType: String? = null
        override val result: RequestResult? = null
        override val errors: List<Error>? = null
    }

    DskCommand.NONE -> throw IllegalStateException("Command must not be NONE")
}

private fun DskContext.toTransportCreate() = TrnCreateResponse(
    responseType = "create",
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    trn = dskResponse.toTransportTrn(),
)

private fun DskContext.toTransportUpdate() = TrnUpdateResponse(
    responseType = "update",
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    trn = dskResponse.toTransportTrn(),
)

private fun DskContext.toTransportRead() = TrnReadResponse(
    responseType = "read",
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    trn = dskResponse.toTransportTrn(),
)

private fun DskContext.toTransportSearch() = TrnSearchResponse(
    responseType = "search",
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    trns = dsksResponse.mapNotNull { it.toTransportTrn() }.takeIf { it.isNotEmpty() },
)

private fun DskContext.toTransportDelete() = TrnDeleteResponse(
    responseType = "delete",
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    trnId = dskResponse.trnId.toTransportUuid(),
)

private fun DskContext.toTransportInit() = TrnInitResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
)

private fun DskState.toTransportResult(): RequestResult = when (this) {
    DskState.FAILED -> RequestResult.ERROR
    DskState.NONE,
    DskState.PROCESSING,
    DskState.FINISHED -> RequestResult.SUCCESS
}

private fun List<DskError>.toTransportErrors(): List<Error>? = map {
    Error(
        code = it.code.takeIf(String::isNotBlank),
        message = it.message.takeIf(String::isNotBlank),
        field = it.field.takeIf(String::isNotBlank),
        group = it.group.takeIf(String::isNotBlank),
    )
}.takeIf { it.isNotEmpty() }

private fun DskTrn.toTransportTrn(): TrnResponseObject? = takeUnless { it.isEmpty() }?.let {
    TrnResponseObject(
        trnId = it.trnId.toTransportUuid(),
        clientFullName = it.clientFullName.takeIf(String::isNotBlank),
        startsAt = it.startsAt.takeUnless { instant -> instant == Instant.NONE }?.toString(),
        durationMin = it.durationMin.toTransportDurationMin(),
        type = it.type.toTransportType(),
        planNotes = it.planNotes.takeIf(String::isNotBlank),
        resultNotes = it.resultNotes.takeIf(String::isNotBlank),
        status = it.status.toTransportStatus(),
        paymentStatus = it.paymentStatus.toTransportPaymentStatus(),
    )
}

private fun models.DskTrnId.toTransportUuid(): UUID? =
    get().takeUnless { it == UUID.fromString("00000000-0000-0000-0000-000000000000") }

private fun Duration.toTransportDurationMin(): String? =
    inWholeMinutes.takeIf { it > 0 }?.toString()

private fun DskTrnType.toTransportType(): TrnType? = when (this) {
    DskTrnType.NONE -> null
    DskTrnType.PERSONAL -> TrnType.PERSONAL
    DskTrnType.FUNCTIONAL -> TrnType.FUNCTIONAL
    DskTrnType.STRENGTH -> TrnType.STRENGTH
    DskTrnType.CARDIO -> TrnType.CARDIO
    DskTrnType.CROSS_FIT -> TrnType.CROSS_FIT
    DskTrnType.OTHER -> TrnType.OTHER
}

private fun DskTrnStatus.toTransportStatus(): TrnStatus? = when (this) {
    DskTrnStatus.NONE -> null
    DskTrnStatus.PLANNED -> TrnStatus.PLANNED
    DskTrnStatus.DONE -> TrnStatus.DONE
    DskTrnStatus.CANCELED -> TrnStatus.CANCELED
}

private fun DskTrnPaymentStatus.toTransportPaymentStatus(): TrnPaymentStatus? = when (this) {
    DskTrnPaymentStatus.NONE -> null
    DskTrnPaymentStatus.UNPAID -> TrnPaymentStatus.UNPAID
    DskTrnPaymentStatus.PAID -> TrnPaymentStatus.PAID
}
