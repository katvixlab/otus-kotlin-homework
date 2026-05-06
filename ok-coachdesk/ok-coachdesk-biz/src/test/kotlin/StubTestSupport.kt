import kotlinx.datetime.Instant
import models.DskClientId
import models.DskCoachId
import models.DskCommand
import models.DskState
import models.DskTrn
import models.DskTrnFilter
import models.DskTrnId
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import models.DskWorkMode
import stubs.DskStubs
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

object StubTestData {
    val trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-000000000099"))
    val coachId = DskCoachId(UUID.fromString("12345678-1111-1234-0000-000000000088"))
    val clientId = DskClientId(UUID.fromString("12345678-1111-1234-0000-000000000077"))
    val startsAt = Instant.parse("2026-07-08T10:15:30Z")
    const val clientFullName = "Петров Афанасий Игнатьевич"

    val request = DskTrn(
        trnId = trnId,
        coachId = coachId,
        clientId = clientId,
        clientFullName = clientFullName,
        startsAt = startsAt,
        durationMin = 90.minutes,
        type = DskTrnType.PERSONAL,
        planNotes = "plan",
        resultNotes = "result",
        status = DskTrnStatus.PLANNED,
        paymentStatus = DskTrnPaymentStatus.UNPAID,
    )

    val filter = DskTrnFilter(
        clientFullName = clientFullName,
        startsAt = startsAt,
        type = DskTrnType.PERSONAL,
        status = DskTrnStatus.PLANNED,
        paymentStatus = DskTrnPaymentStatus.UNPAID,
    )
}

fun stubContext(
    command: DskCommand,
    stubCase: DskStubs,
    request: DskTrn = StubTestData.request,
    filter: DskTrnFilter = StubTestData.filter,
) = DskContext(
    command = command,
    state = DskState.NONE,
    workMode = DskWorkMode.STUB,
    stubCase = stubCase,
    dskRequest = request,
    dskTrnFilter = filter,
)

fun assertStubError(
    ctx: DskContext,
    group: String = "validation",
    code: String,
    field: String = "",
) {
    assertEquals(DskState.FAILED, ctx.state)
    assertEquals(DskTrn(), ctx.dskResponse)
    assertEquals(group, ctx.errors.firstOrNull()?.group)
    assertEquals(code, ctx.errors.firstOrNull()?.code)
    assertEquals(field, ctx.errors.firstOrNull()?.field)
}

fun assertSuccess(ctx: DskContext) {
    assertEquals(DskState.FINISHED, ctx.state)
    assertTrue(ctx.errors.isEmpty())
}
