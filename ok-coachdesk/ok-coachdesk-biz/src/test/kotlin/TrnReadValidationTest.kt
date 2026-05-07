import kotlinx.coroutines.test.runTest
import models.DskCommand
import models.DskTrn
import models.DskTrnId
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnReadValidationTest {

    private val processor = DskProcessor()

    @Test
    fun success() = runTest {
        val ctx = validationContext(DskCommand.READ, DskTrn(trnId = StubTestData.trnId))

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals(StubTestData.trnId.get(), ctx.trnValidated.trnId.get())
    }

    @Test
    fun emptyTrnId() = runTest {
        val ctx = validationContext(DskCommand.READ, DskTrn(trnId = DskTrnId.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "trnId", "empty")
    }
}
