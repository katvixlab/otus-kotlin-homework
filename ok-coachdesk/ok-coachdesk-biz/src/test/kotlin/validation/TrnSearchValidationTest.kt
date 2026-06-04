package validation

import NONE
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import models.DskCommand
import models.DskTrnFilter
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import stub.StubTestData
import stub.assertValidationError
import stub.assertValidationSuccess
import stub.validationContext
import stub.validationProcessor
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnSearchValidationTest {

    private val processor = validationProcessor()

    @Test
    fun success() = runTest {
        val ctx = validationContext(
            command = DskCommand.SEARCH,
            filter = StubTestData.filter.copy(clientFullName = "  Иванов Иван  "),
        )

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals("Иванов Иван", ctx.trnFilterValidated.clientFullName)
    }

    @Test
    fun emptyClientFullNameAllowed() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(clientFullName = "   "))

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals("", ctx.trnFilterValidated.clientFullName)
    }

    @Test
    fun emptyStartsAtAllowed() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(startsAt = Instant.NONE))

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals(Instant.NONE, ctx.trnFilterValidated.startsAt)
    }

    @Test
    fun emptyTypeAllowed() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(type = DskTrnType.NONE))

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals(DskTrnType.NONE, ctx.trnFilterValidated.type)
    }

    @Test
    fun emptyStatusAllowed() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(status = DskTrnStatus.NONE))

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals(DskTrnStatus.NONE, ctx.trnFilterValidated.status)
    }

    @Test
    fun emptyPaymentStatusAllowed() = runTest {
        val ctx = validationContext(
            DskCommand.SEARCH,
            filter = StubTestData.filter.copy(paymentStatus = DskTrnPaymentStatus.NONE)
        )

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals(DskTrnPaymentStatus.NONE, ctx.trnFilterValidated.paymentStatus)
    }

    @Test
    fun emptyFilter() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = DskTrnFilter())

        processor.exec(ctx)

        assertValidationError(ctx, "all", "empty")
    }
}
