import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import models.DskCommand
import models.DskTrnId
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class TrnUpdateValidationTest {

    private val processor = DskProcessor()

    @Test
    fun successWithPastStartsAt() = runTest {
        val ctx = validationContext(
            command = DskCommand.UPDATE,
            request = StubTestData.request.copy(
                clientFullName = "  Иванов Иван  ",
                startsAt = Instant.parse("2025-12-31T00:00:00Z"),
            ),
            timeStart = Instant.parse("2026-01-02T00:00:00Z"),
        )

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals(StubTestData.trnId.get(), ctx.trnValidated.trnId.get())
        assertEquals("Иванов Иван", ctx.trnValidated.clientFullName)
    }

    @Test
    fun emptyTrnId() = runTest {
        val ctx = validationContext(DskCommand.UPDATE, StubTestData.request.copy(trnId = DskTrnId.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "trnId", "empty")
    }

    @Test
    fun tooShortDuration() = runTest {
        val ctx = validationContext(DskCommand.UPDATE, StubTestData.request.copy(durationMin = 5.minutes))

        processor.exec(ctx)

        assertValidationError(ctx, "durationMin", "range")
    }
}
