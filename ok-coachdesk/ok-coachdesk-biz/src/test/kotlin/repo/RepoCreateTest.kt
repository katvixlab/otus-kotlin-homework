package repo

import DskContext
import DskCorSettings
import TrnRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import models.*
import org.junit.Test
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.Duration

class RepoCreateTest {

    private val uuid = UUID.fromString("12345678-1111-1234-0000-0000000000")
    private val command = DskCommand.CREATE
    private val repo = TrnRepositoryMock(
        invokeCreateTrn = {
            DbTrnResponseOk(
                data = DskTrn(
                    trnId = DskTrnId(uuid),
                    coachId = it.trn.coachId,
                    clientId = it.trn.clientId,
                    clientFullName = it.trn.clientFullName,
                    startsAt = it.trn.startsAt,
                    durationMin = it.trn.durationMin,
                    type = it.trn.type,
                    paymentStatus = it.trn.paymentStatus,
                    status = it.trn.status,
                    planNotes = it.trn.planNotes,
                    resultNotes = it.trn.resultNotes,
                )
            )
        }
    )

    private val settings = DskCorSettings(
        repoTest = repo,
    )

    private val processor = DskProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val context = DskContext(
            command = command,
            state = DskState.NONE,
            workMode = DskWorkMode.TEST,
            trnRequest = DskTrn(
                trnId = DskTrnId(uuid),
                coachId = DskCoachId(uuid),
                clientId = DskClientId(uuid),
                clientFullName = "test test",
                startsAt = Instant.parse("2030-07-08T10:15:30+01:00"),
                durationMin = Duration.parse("90m"),
                type = DskTrnType.PERSONAL,
                planNotes = "plane",
                resultNotes = "result",
                status = DskTrnStatus.PLANNED,
                paymentStatus = DskTrnPaymentStatus.UNPAID,
            )
        )

        processor.exec(context)
        print(context.errors)
        assertEquals(DskState.FINISHED, context.state)
        assertNotEquals(DskTrnId.NONE, context.trnResponse.trnId)
        assertEquals("test test", context.trnResponse.clientFullName)
    }
}