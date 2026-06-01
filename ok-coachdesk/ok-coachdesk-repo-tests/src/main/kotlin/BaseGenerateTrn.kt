import kotlinx.datetime.Instant
import models.DskClientId
import models.DskCoachId
import models.DskTrn
import models.DskTrnId
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import java.util.UUID
import kotlin.time.Duration

abstract class BaseGenerateTrn(private val command: String): IGenerateObjects<DskTrn> {
    fun createTestModel(
        suf: String,
        type: DskTrnType = DskTrnType.PERSONAL,
        status: DskTrnStatus = DskTrnStatus.PLANNED,
        paymentStatus: DskTrnPaymentStatus = DskTrnPaymentStatus.UNPAID
    ) = DskTrn(
        trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000000")),
        coachId = DskCoachId(UUID.fromString("12345678-1111-1234-0000-0000000000")),
        clientId = DskClientId(UUID.fromString("12345678-1111-1234-0000-000000000000")),
        clientFullName = "$suf-stub",
        startsAt = Instant.parse("2026-07-08T10:15:30+01:00"),
        durationMin = Duration.parse("90m"),
        type = type,
        planNotes = "plane",
        resultNotes = "result",
        status = status,
        paymentStatus = paymentStatus
    )
}