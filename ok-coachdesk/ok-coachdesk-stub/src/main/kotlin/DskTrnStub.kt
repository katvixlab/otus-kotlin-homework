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

object DskTrnStub {
    val AD_TRN: DskTrn
    get() = DskTrn(
        trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000000")),
        coachId = DskCoachId(UUID.fromString("12345678-1111-1234-0000-0000000000")),
        clientId = DskClientId(UUID.fromString("12345678-1111-1234-0000-000000000000")),
        clientFullName = "Ivan Ivanov",
        startsAt = Instant.parse("2026-07-08T10:15:30+01:00"),
        durationMin = Duration.parse("90m"),
        type = DskTrnType.PERSONAL,
        planNotes = "plane",
        resultNotes = "result",
        status = DskTrnStatus.PLANNED,
        paymentStatus = DskTrnPaymentStatus.UNPAID
        )

}