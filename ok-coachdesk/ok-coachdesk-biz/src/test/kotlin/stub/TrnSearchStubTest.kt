package stub

import DskStub
import kotlinx.coroutines.test.runTest
import models.DskCommand
import models.DskTrn
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import stubs.DskStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnSearchStubTest {

    private val processor = DskProcessor()

    @Test
    fun success() = runTest {
        val ctx = stubContext(DskCommand.SEARCH, DskStubs.SUCCESS)

        processor.exec(ctx)

        assertSuccess(ctx)
        assertEquals(6, ctx.trnsResponse.size)
        assertEquals(DskStub.get().clientFullName, ctx.trnsResponse.first().clientFullName)
    }

    @Test
    fun mismatchSearchString() = runTest {
        val ctx = stubContext(DskCommand.SEARCH, DskStubs.MISMATCH_SEARCH_STRING)

        processor.exec(ctx)

        assertStubError(ctx, code = "validation-search-string", field = "clientFullName")
    }

    @Test
    fun databaseError() = runTest {
        val ctx = stubContext(DskCommand.SEARCH, DskStubs.DB_ERROR)

        processor.exec(ctx)

        assertEquals(emptyList(), ctx.trnsResponse)
        assertStubError(ctx, group = "internal", code = "internal-db")
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = stubContext(DskCommand.SEARCH, DskStubs.BAD_FIELD)

        processor.exec(ctx)

        assertEquals(DskTrn(), ctx.trnResponse)
        assertStubError(ctx, group = "stub", code = "unsupported-stub", field = "stub")
    }
}
