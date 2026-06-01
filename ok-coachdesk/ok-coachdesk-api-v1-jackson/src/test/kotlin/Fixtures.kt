import ru.otus.kotlin.coachdesk.api.v1.models.BaseTrn
import ru.otus.kotlin.coachdesk.api.v1.models.Error
import ru.otus.kotlin.coachdesk.api.v1.models.IdTrn
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDebug
import ru.otus.kotlin.coachdesk.api.v1.models.TrnFilter
import ru.otus.kotlin.coachdesk.api.v1.models.TrnPaymentStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestObject
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestDebugMode
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestDebugStubs
import ru.otus.kotlin.coachdesk.api.v1.models.TrnResponseObject
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchFilter
import ru.otus.kotlin.coachdesk.api.v1.models.TrnStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnType
import ru.otus.kotlin.coachdesk.api.v1.models.TrnWithIdRequestObject
import java.time.Instant
import java.util.UUID

val trnIdFixture: UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
val coachIdFixture: UUID = UUID.fromString("123e4567-e89b-12d3-a456-425515174000")

val idTrnFixture = IdTrn(
    trnId = trnIdFixture
)

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
    coachId = coachIdFixture
)

val trnWithIdRequestObjectFixture = TrnWithIdRequestObject(
    trnId = trnIdFixture,
    clientFullName = "client",
    startsAt = Instant.parse("2026-03-30T18:00:00Z").toString(),
    durationMin = "90",
    type = TrnType.PERSONAL,
    planNotes = "plan",
    status = TrnStatus.DONE,
    resultNotes = "result",
    paymentStatus = TrnPaymentStatus.PAID,
    coachId = coachIdFixture
)

val trnResponseObjectFixtures = TrnResponseObject(
    trnId = trnIdFixture,
    clientFullName = "client",
    startsAt = Instant.parse("2026-03-30T18:00:00Z").toString(),
    durationMin = "90",
    type = TrnType.PERSONAL,
    planNotes = "plan",
    status = TrnStatus.DONE,
    resultNotes = "result",
    paymentStatus = TrnPaymentStatus.PAID
)

val trnSearchFilterFixture = TrnFilter(
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
