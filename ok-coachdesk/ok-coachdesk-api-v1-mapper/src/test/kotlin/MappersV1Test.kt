import kotlinx.datetime.Instant
import models.DskCommand
import models.DskError
import models.DskState
import models.DskTrn
import models.DskTrnId
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import models.DskWorkMode
import ru.otus.kotlin.coachdesk.api.v1.models.IdTrn
import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDebug
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnFilter
import ru.otus.kotlin.coachdesk.api.v1.models.TrnPaymentStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestDebugMode
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestDebugStubs
import ru.otus.kotlin.coachdesk.api.v1.models.TrnRequestObject
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnStatus
import ru.otus.kotlin.coachdesk.api.v1.models.TrnType
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateResponse
import stubs.DskStubs
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.minutes

class MappersV1Test {
    private val trnId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
    private val coachId = UUID.fromString("123e4567-e89b-12d3-a456-425515174000")
    private val startsAt = Instant.parse("2026-03-30T18:00:00Z")

    private val transportDebug = TrnDebug(
        mode = TrnRequestDebugMode.TEST,
        stub = TrnRequestDebugStubs.SUCCESS,
    )

    private val transportTrn = TrnRequestObject(
        trnId = trnId,
        coachId = coachId,
        clientFullName = "client",
        startsAt = startsAt.toString(),
        durationMin = "90",
        type = TrnType.PERSONAL,
        planNotes = "plan",
        resultNotes = "result",
        status = TrnStatus.DONE,
        paymentStatus = TrnPaymentStatus.PAID,
    )

    private val internalTrn = DskTrn(
        trnId = DskTrnId(trnId),
        coachId = models.DskCoachId(coachId),
        clientFullName = "client",
        startsAt = startsAt,
        durationMin = 90.minutes,
        type = DskTrnType.PERSONAL,
        planNotes = "plan",
        resultNotes = "result",
        status = DskTrnStatus.DONE,
        paymentStatus = DskTrnPaymentStatus.PAID,
    )

    private val error = DskError(
        code = "duplicated-code",
        group = "validation",
        field = "clientFullName",
        message = "duplicated-message",
    )

    @Test
    fun fromTransportCreate() {
        val context = DskContext()

        context.fromTransport(
            TrnCreateRequest(
                requestType = "create",
                debug = transportDebug,
                trn = transportTrn,
            )
        )

        assertEquals(DskCommand.CREATE, context.command)
        assertEquals(DskWorkMode.TEST, context.workMode)
        assertEquals(DskStubs.SUCCESS, context.stubCase)
        assertEquals(DskTrnId(trnId).get(), context.trnRequest.trnId.get())
        assertEquals(models.DskCoachId(coachId).get(), context.trnRequest.coachId.get())
        assertEquals("client", context.trnRequest.clientFullName)
        assertEquals(startsAt, context.trnRequest.startsAt)
        assertEquals(90.minutes, context.trnRequest.durationMin)
        assertEquals(DskTrnType.PERSONAL, context.trnRequest.type)
        assertEquals("plan", context.trnRequest.planNotes)
        assertEquals("result", context.trnRequest.resultNotes)
        assertEquals(DskTrnStatus.DONE, context.trnRequest.status)
        assertEquals(DskTrnPaymentStatus.PAID, context.trnRequest.paymentStatus)
    }

    @Test
    fun toTransportCreate() {
        val context = DskContext(
            command = DskCommand.CREATE,
            state = DskState.FINISHED,
            trnResponse = internalTrn,
            errors = mutableListOf(error),
        )

        val response = context.toTransport()

        assertIs<TrnCreateResponse>(response)
        assertEquals("create", response.responseType)
        assertEquals(RequestResult.SUCCESS, response.result)
        assertEquals("duplicated-code", response.errors?.first()?.code)
        assertEquals(trnId, response.trn?.trnId)
        assertEquals("client", response.trn?.clientFullName)
        assertEquals("90", response.trn?.durationMin)
        assertEquals(TrnType.PERSONAL, response.trn?.type)
    }

    @Test
    fun fromTransportUpdate() {
        val context = DskContext()

        context.fromTransport(
            TrnUpdateRequest(
                requestType = "update",
                debug = transportDebug,
                trn = transportTrn,
            )
        )

        assertEquals(DskCommand.UPDATE, context.command)
        assertEquals(DskWorkMode.TEST, context.workMode)
        assertEquals(DskStubs.SUCCESS, context.stubCase)
        assertEquals("client", context.trnRequest.clientFullName)
        assertEquals(90.minutes, context.trnRequest.durationMin)
    }

    @Test
    fun toTransportUpdate() {
        val context = DskContext(
            command = DskCommand.UPDATE,
            state = DskState.FINISHED,
            trnResponse = internalTrn,
            errors = mutableListOf(error),
        )

        val response = context.toTransport()

        assertIs<TrnUpdateResponse>(response)
        assertEquals("update", response.responseType)
        assertEquals(RequestResult.SUCCESS, response.result)
        assertEquals(trnId, response.trn?.trnId)
        assertEquals("result", response.trn?.resultNotes)
    }

    @Test
    fun fromTransportRead() {
        val context = DskContext()

        context.fromTransport(
            TrnReadRequest(
                requestType = "read",
                debug = transportDebug,
                trn = IdTrn(
                    trnId = trnId,
                    coachId = coachId
                ),
            )
        )

        assertEquals(DskCommand.READ, context.command)
        assertEquals(DskWorkMode.TEST, context.workMode)
        assertEquals(DskStubs.SUCCESS, context.stubCase)
        assertEquals(trnId, context.trnRequest.trnId.get())
        assertEquals(coachId, context.trnRequest.coachId.get())
    }

    @Test
    fun toTransportRead() {
        val context = DskContext(
            command = DskCommand.READ,
            state = DskState.FINISHED,
            trnResponse = internalTrn,
            errors = mutableListOf(error),
        )

        val response = context.toTransport()

        assertIs<TrnReadResponse>(response)
        assertEquals("read", response.responseType)
        assertEquals(RequestResult.SUCCESS, response.result)
        assertEquals(trnId, response.trn?.trnId)
        assertEquals(TrnStatus.DONE, response.trn?.status)
    }

    @Test
    fun fromTransportSearch() {
        val context = DskContext()

        context.fromTransport(
            TrnSearchRequest(
                requestType = "search",
                debug = transportDebug,
                trnFilter = TrnFilter(
                    clientFullName = "client",
                    startsAt = startsAt.toString(),
                    type = TrnType.PERSONAL,
                    status = TrnStatus.DONE,
                    paymentStatus = TrnPaymentStatus.PAID,
                ),
            )
        )

        assertEquals(DskCommand.SEARCH, context.command)
        assertEquals(DskTrn(), context.trnRequest)
    }

    @Test
    fun toTransportSearch() {
        val context = DskContext(
            command = DskCommand.SEARCH,
            state = DskState.FINISHED,
            trnsResponse = mutableListOf(internalTrn),
            errors = mutableListOf(error),
        )

        val response = context.toTransport()

        assertIs<TrnSearchResponse>(response)
        assertEquals("search", response.responseType)
        assertEquals(RequestResult.SUCCESS, response.result)
        assertEquals(1, response.trns?.size)
        assertEquals(trnId, response.trns?.first()?.trnId)
        assertEquals(TrnPaymentStatus.PAID, response.trns?.first()?.paymentStatus)
    }

    @Test
    fun fromTransportDelete() {
        val context = DskContext()

        context.fromTransport(
            TrnDeleteRequest(
                requestType = "delete",
                debug = transportDebug,
                trn = IdTrn(
                    trnId = trnId,
                    coachId = coachId,
                ),
            )
        )

        assertEquals(DskCommand.DELETE, context.command)
        assertEquals(DskWorkMode.TEST, context.workMode)
        assertEquals(DskStubs.SUCCESS, context.stubCase)
        assertEquals(trnId, context.trnRequest.trnId.get())
        assertEquals(coachId, context.trnRequest.coachId.get())
    }

    @Test
    fun toTransportDelete() {
        val context = DskContext(
            command = DskCommand.DELETE,
            state = DskState.FAILED,
            trnResponse = DskTrn(trnId = DskTrnId(trnId)),
            errors = mutableListOf(error),
        )

        val response = context.toTransport()

        assertIs<TrnDeleteResponse>(response)
        assertEquals("delete", response.responseType)
        assertEquals(RequestResult.ERROR, response.result)
        assertEquals("duplicated-code", response.errors?.first()?.code)
        assertEquals(trnId, response.trnId)
    }
}
