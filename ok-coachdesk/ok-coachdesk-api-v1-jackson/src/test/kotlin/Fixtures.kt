import ru.otus.kotlin.coachdesk.api.v1.models.BaseTrn
import ru.otus.kotlin.coachdesk.api.v1.models.Error
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDebug
import ru.otus.kotlin.coachdesk.api.v1.models.TrnPaymentStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestObject
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestDebugMode
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestDebugStubs
import ru.otus.kotlin.coachdesk.api.v1.models.TrnResponseObject
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchFilter
import ru.otus.kotlin.coachdesk.api.v1.models.TrnStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnType
import java.time.Instant
import java.util.UUID

val trnIdFixture: UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

val baseTrnFixture = BaseTrn(
    clientFullName = "client",
    startsAt = Instant.parse("2026-03-30T18:00:00Z").toString(),
    durationMin = "90",
    type = TrnType.PERSONAL,
    planNotes = "plan",
    status = TrnStatus.DONE,
    resultNotes = "result",
    paymentStatus = TrnPaymentStatus.PAID
)

val trnDebugFixture = TrnDebug(
    mode = TrnRequestDebugMode.TEST,
    stub = TrnRequestDebugStubs.SUCCESS
)

val trnRequestObjectFixture = TrnRequestObject(
    clientFullName = "client",
    startsAt = Instant.parse("2026-03-30T18:00:00Z").toString(),
    durationMin = "90",
    type = TrnType.PERSONAL,
    planNotes = "plan",
    status = TrnStatus.DONE,
    resultNotes = "result",
    paymentStatus = TrnPaymentStatus.PAID,
    id = trnIdFixture
)

val trnResponseObjectFixtures = TrnResponseObject(
    id = trnIdFixture,
    clientFullName = "client",
    startsAt = Instant.parse("2026-03-30T18:00:00Z").toString(),
    durationMin = "90",
    type = TrnType.PERSONAL,
    planNotes = "plan",
    status = TrnStatus.DONE,
    resultNotes = "result",
    paymentStatus = TrnPaymentStatus.PAID
)

val trnSearchFilterFixture = TrnSearchFilter(
    clientFullName = "client",
    startsAt = Instant.parse("2026-03-30T18:00:00Z").toString(),
    type = TrnType.PERSONAL,
    status = TrnStatus.DONE,
    paymentStatus = TrnPaymentStatus.PAID
)

val errorFixture = Error(
    code = "duplicated-code",
    message = "duplicated-message",
    field = "duplicated-field",
    group = "duplicated-group"
)
