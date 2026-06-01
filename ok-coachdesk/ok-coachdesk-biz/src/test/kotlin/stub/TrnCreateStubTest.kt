package stub

import kotlinx.coroutines.test.runTest
import models.DskCommand
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import stubs.DskStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnCreateStubTest {

    private val processor = DskProcessor()

    @Test
    fun success() = runTest {
        val ctx = stubContext(DskCommand.CREATE, DskStubs.SUCCESS)

        processor.exec(ctx)

        assertSuccess(ctx)
        assertEquals(StubTestData.clientFullName, ctx.trnResponse.clientFullName)
        assertEquals(StubTestData.coachId, ctx.trnResponse.coachId)
        assertEquals(StubTestData.clientId, ctx.trnResponse.clientId)
        assertEquals(StubTestData.startsAt, ctx.trnResponse.startsAt)
    }

    @Test
    fun badField() = runTest {
        val ctx = stubContext(DskCommand.CREATE, DskStubs.BAD_FIELD)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-field", field = "field")
    }

    @Test
    fun badCoachId() = runTest {
        val ctx = stubContext(DskCommand.CREATE, DskStubs.BAD_COACH_ID)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-coach-id", field = "coachId")
    }

    @Test
    fun badClientId() = runTest {
        val ctx = stubContext(DskCommand.CREATE, DskStubs.BAD_CLIENT_ID)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-client-id", field = "clientId")
    }

    @Test
    fun badStartsAt() = runTest {
        val ctx = stubContext(DskCommand.CREATE, DskStubs.BAD_STARTS_AT)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-starts-at", field = "startsAt")
    }

    @Test
    fun databaseError() = runTest {
        val ctx = stubContext(DskCommand.CREATE, DskStubs.DB_ERROR)

        processor.exec(ctx)

        assertStubError(ctx, group = "internal", code = "internal-db")
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = stubContext(DskCommand.CREATE, DskStubs.BAD_ID)

        processor.exec(ctx)

        assertStubError(ctx, group = "stub", code = "unsupported-stub", field = "stub")
    }
}
