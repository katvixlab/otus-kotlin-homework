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

class RepoSearchTest {

    private val uuid = UUID.fromString("12345678-1111-1234-0000-0000000000")
    private val filter = DskTrnFilter(
        clientFullName = "  test test  ",
        startsAt = Instant.parse("2030-07-08T10:15:30+01:00"),
        type = DskTrnType.PERSONAL,
        status = DskTrnStatus.PLANNED,
        paymentStatus = DskTrnPaymentStatus.UNPAID,
    )
    private val expected = listOf(
        DskTrn(
            trnId = DskTrnId(uuid),
            clientFullName = "test test",
        )
    )
    private val repo = TrnRepositoryMock(
        invokeSearchTrn = {
            assertEquals("test test", it.clientFullName)
            assertEquals(filter.startsAt, it.startsAt)
            assertEquals(filter.type, it.type)
            assertEquals(filter.status, it.status)
            assertEquals(filter.paymentStatus, it.paymentStatus)
            DbTrnsResponseOk(expected)
        }
    )
    private val processor = DskProcessor(
        DskCorSettings(repoTest = repo)
    )

    @Test
    fun repoSearchSuccessTest() = runTest {
        val context = DskContext(
            command = DskCommand.SEARCH,
            state = DskState.NONE,
            workMode = DskWorkMode.TEST,
            trnFilterRequest = filter,
        )

        processor.exec(context)

        assertEquals(DskState.FINISHED, context.state)
        assertEquals(expected, context.trnsResponse)
    }
}
