package stub

import kotlinx.coroutines.test.runTest
import models.DskCommand
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import stubs.DskStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnDeleteStubTest {

    private val processor = DskProcessor()

    @Test
    fun success() = runTest {
        val ctx = stubContext(DskCommand.DELETE, DskStubs.SUCCESS)

        processor.exec(ctx)

        assertSuccess(ctx)
        assertEquals(StubTestData.trnId.get(), ctx.trnResponse.trnId.get())
        assertEquals(StubTestData.coachId, ctx.trnResponse.coachId)
    }

    @Test
    fun badId() = runTest {
        val ctx = stubContext(DskCommand.DELETE, DskStubs.BAD_ID)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-id", field = "trnId")
    }

    @Test
    fun notFound() = runTest {
        val ctx = stubContext(DskCommand.DELETE, DskStubs.NOT_FOUND)

        processor.exec(ctx)

        assertStubError(ctx, group = "not-found", code = "not-found", field = "trnId")
    }

    @Test
    fun cannotDelete() = runTest {
        val ctx = stubContext(DskCommand.DELETE, DskStubs.CANNOT_DELETE)

        processor.exec(ctx)

        assertStubError(ctx, code = "cannot-delete", field = "trnId")
    }

    @Test
    fun databaseError() = runTest {
        val ctx = stubContext(DskCommand.DELETE, DskStubs.DB_ERROR)

        processor.exec(ctx)

        assertStubError(ctx, group = "internal", code = "internal-db")
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = stubContext(DskCommand.DELETE, DskStubs.BAD_FIELD)

        processor.exec(ctx)

        assertStubError(ctx, group = "stub", code = "unsupported-stub", field = "stub")
    }
}
