package repo

import DskContext
import DskCorSettings
import TrnRepositoryMock
import kotlinx.coroutines.test.runTest
import models.DskCommand
import models.DskState
import models.DskTrn
import models.DskTrnId
import models.DskWorkMode
import org.junit.Test
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import java.util.UUID
import kotlin.test.assertEquals

class RepoDeleteTest {

    private val uuid = UUID.fromString("12345678-1111-1234-0000-0000000000")
    private val expected = DskTrn(
        trnId = DskTrnId(uuid),
        clientFullName = "test test",
    )
    private val repo = TrnRepositoryMock(
        invokeReadTrn = {
            assertEquals(DskTrnId(uuid), it.trnId)
            DbTrnResponseOk(expected)
        },
        invokeDeleteTrn = {
            assertEquals(DskTrnId(uuid), it.trnId)
            DbTrnResponseOk(expected)
        },
    )
    private val processor = DskProcessor(
        DskCorSettings(repoTest = repo)
    )

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val context = DskContext(
            command = DskCommand.DELETE,
            state = DskState.NONE,
            workMode = DskWorkMode.TEST,
            trnRequest = DskTrn(trnId = DskTrnId(uuid)),
        )

        processor.exec(context)

        assertEquals(DskState.FINISHED, context.state)
        assertEquals(expected, context.trnResponse)
    }
}
