package repo

import DskContext
import DskCorSettings
import TrnRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import models.*
import org.junit.Test
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class RepoUpdateTest {

    private val uuid = UUID.fromString("12345678-1111-1234-0000-0000000000")
    private val stored = DskTrn(
        trnId = DskTrnId(uuid),
        coachId = DskCoachId(uuid),
        clientId = DskClientId(uuid),
        clientFullName = "stored client",
        startsAt = Instant.parse("2030-07-08T10:15:30+01:00"),
        durationMin = 60.minutes,
        type = DskTrnType.PERSONAL,
        planNotes = "stored plan",
        resultNotes = "stored result",
        status = DskTrnStatus.PLANNED,
        paymentStatus = DskTrnPaymentStatus.UNPAID,
    )
    private val request = stored.copy(
        clientFullName = "request client",
        startsAt = Instant.parse("2030-08-08T10:15:30+01:00"),
        durationMin = 90.minutes,
        type = DskTrnType.CARDIO,
        planNotes = "updated plan",
        resultNotes = "updated result",
        status = DskTrnStatus.DONE,
        paymentStatus = DskTrnPaymentStatus.PAID,
    )
    private val expected = stored.copy(
        startsAt = request.startsAt,
        durationMin = request.durationMin,
        type = request.type,
        planNotes = request.planNotes,
        resultNotes = request.resultNotes,
        status = request.status,
        paymentStatus = request.paymentStatus,
    )
    private val repo = TrnRepositoryMock(
        invokeReadTrn = {
            assertEquals(DskTrnId(uuid), it.trnId)
            DbTrnResponseOk(stored)
        },
        invokeUpdateTrn = {
            assertEquals(expected, it.trn)
            DbTrnResponseOk(it.trn)
        },
    )
    private val processor = DskProcessor(
        DskCorSettings(repoTest = repo)
    )

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val context = DskContext(
            command = DskCommand.UPDATE,
            state = DskState.NONE,
            workMode = DskWorkMode.TEST,
            trnRequest = request,
        )

        processor.exec(context)

        assertEquals(DskState.FINISHED, context.state)
        assertEquals(expected, context.trnResponse)
    }
}
