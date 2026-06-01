package validation

import kotlinx.coroutines.test.runTest
import models.DskCommand
import models.DskTrn
import models.DskTrnId
import stub.StubTestData
import stub.assertValidationError
import stub.assertValidationSuccess
import stub.validationContext
import stub.validationProcessor
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnReadValidationTest {

    private val processor = validationProcessor()

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
