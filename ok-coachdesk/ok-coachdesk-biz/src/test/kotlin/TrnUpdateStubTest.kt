import kotlinx.coroutines.test.runTest
import models.DskCommand
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import stubs.DskStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnUpdateStubTest {

    private val processor = DskProcessor()

    @Test
    fun success() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.SUCCESS)

        processor.exec(ctx)

        assertSuccess(ctx)
        assertEquals(StubTestData.trnId.get(), ctx.dskResponse.trnId.get())
        assertEquals(StubTestData.clientFullName, ctx.dskResponse.clientFullName)
        assertEquals(StubTestData.startsAt, ctx.dskResponse.startsAt)
    }

    @Test
    fun badId() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.BAD_ID)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-id", field = "trnId")
    }

    @Test
    fun badField() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.BAD_FIELD)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-field", field = "field")
    }

    @Test
    fun badCoachId() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.BAD_COACH_ID)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-coach-id", field = "coachId")
    }

    @Test
    fun badClientId() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.BAD_CLIENT_ID)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-client-id", field = "clientId")
    }

    @Test
    fun badStartsAt() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.BAD_STARTS_AT)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-starts-at", field = "startsAt")
    }

    @Test
    fun notFound() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.NOT_FOUND)

        processor.exec(ctx)

        assertStubError(ctx, group = "not-found", code = "not-found", field = "trnId")
    }

    @Test
    fun databaseError() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.DB_ERROR)

        processor.exec(ctx)

        assertStubError(ctx, group = "internal", code = "internal-db")
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = stubContext(DskCommand.UPDATE, DskStubs.CANNOT_DELETE)

        processor.exec(ctx)

        assertStubError(ctx, group = "stub", code = "unsupported-stub", field = "stub")
    }
}
