import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import models.DskCommand
import models.DskTrnFilter
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import kotlin.test.Test
import kotlin.test.assertEquals

class TrnSearchValidationTest {

    private val processor = DskProcessor()

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
    fun emptyClientFullName() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(clientFullName = "   "))

        processor.exec(ctx)

        assertValidationError(ctx, "clientFullName", "empty")
    }

    @Test
    fun emptyStartsAt() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(startsAt = Instant.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "startsAt", "empty")
    }

    @Test
    fun emptyType() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(type = DskTrnType.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "type", "empty")
    }

    @Test
    fun emptyStatus() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(status = DskTrnStatus.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "status", "empty")
    }

    @Test
    fun emptyPaymentStatus() = runTest {
        val ctx = validationContext(DskCommand.SEARCH, filter = StubTestData.filter.copy(paymentStatus = DskTrnPaymentStatus.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "paymentStatus", "empty")
    }
}
