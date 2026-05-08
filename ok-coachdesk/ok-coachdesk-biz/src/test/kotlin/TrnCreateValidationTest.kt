import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import models.DskClientId
import models.DskCoachId
import models.DskCommand
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import ru.otus.kotlin.coachdesk.biz.DskProcessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class TrnCreateValidationTest {

    private val processor = DskProcessor()

    @Test
    fun success() = runTest {
        val ctx = validationContext(
            command = DskCommand.CREATE,
            request = StubTestData.request.copy(
                trnId = models.DskTrnId.NONE,
                clientFullName = "  Иванов Иван Иванович  ",
                planNotes = "  план  ",
                resultNotes = "  итог  ",
            )
        )

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals(models.DskTrnId.NONE.get(), ctx.trnValidated.trnId.get())
        assertEquals("Иванов Иван Иванович", ctx.trnValidated.clientFullName)
        assertEquals("план", ctx.trnValidated.planNotes)
        assertEquals("итог", ctx.trnValidated.resultNotes)
    }

    @Test
    fun badCoachId() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(coachId = DskCoachId.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "coachId", "empty")
    }

    @Test
    fun badClientId() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(clientId = DskClientId.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "clientId", "empty")
    }

    @Test
    fun emptyClientFullName() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(clientFullName = "   "))

        processor.exec(ctx)

        assertValidationError(ctx, "clientFullName", "empty")
    }

    @Test
    fun badClientFullNameOneWord() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(clientFullName = "Иванов"))

        processor.exec(ctx)

        assertValidationError(ctx, "clientFullName", "format")
    }

    @Test
    fun badClientFullNameShortWord() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(clientFullName = "И Петров"))

        processor.exec(ctx)

        assertValidationError(ctx, "clientFullName", "format")
    }

    @Test
    fun badClientFullNameLongWord() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(clientFullName = "Оченьдлиннаяфамилияклиента Иван"))

        processor.exec(ctx)

        assertValidationError(ctx, "clientFullName", "format")
    }

    @Test
    fun emptyStartsAt() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(startsAt = Instant.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "startsAt", "empty")
    }

    @Test
    fun pastStartsAt() = runTest {
        val ctx = validationContext(
            command = DskCommand.CREATE,
            request = StubTestData.request.copy(startsAt = Instant.parse("2026-01-01T00:00:00Z")),
            timeStart = Instant.parse("2026-01-02T00:00:00Z"),
        )

        processor.exec(ctx)

        assertValidationError(ctx, "startsAt", "past")
    }

    @Test
    fun tooShortDuration() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(durationMin = 9.minutes))

        processor.exec(ctx)

        assertValidationError(ctx, "durationMin", "range")
    }

    @Test
    fun tooLongDuration() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(durationMin = 8.hours + 1.minutes))

        processor.exec(ctx)

        assertValidationError(ctx, "durationMin", "range")
    }

    @Test
    fun emptyType() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(type = DskTrnType.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "type", "empty")
    }

    @Test
    fun garbagePlanNotes() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(planNotes = "!!!"))

        processor.exec(ctx)

        assertValidationError(ctx, "planNotes", "no-text")
    }

    @Test
    fun emptyPlanNotesIsAllowed() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(planNotes = "   "))

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals("", ctx.trnValidated.planNotes)
    }

    @Test
    fun garbageResultNotes() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(resultNotes = "?!@"))

        processor.exec(ctx)

        assertValidationError(ctx, "resultNotes", "no-text")
    }

    @Test
    fun emptyResultNotesIsAllowed() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(resultNotes = "   "))

        processor.exec(ctx)

        assertValidationSuccess(ctx)
        assertEquals("", ctx.trnValidated.resultNotes)
    }

    @Test
    fun emptyStatus() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(status = DskTrnStatus.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "status", "empty")
    }

    @Test
    fun emptyPaymentStatus() = runTest {
        val ctx = validationContext(DskCommand.CREATE, StubTestData.request.copy(paymentStatus = DskTrnPaymentStatus.NONE))

        processor.exec(ctx)

        assertValidationError(ctx, "paymentStatus", "empty")
    }
}
