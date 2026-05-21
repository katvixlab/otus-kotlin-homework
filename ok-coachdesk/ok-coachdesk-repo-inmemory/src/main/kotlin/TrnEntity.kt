import kotlinx.datetime.Instant
import models.*
import kotlin.time.Duration

data class TrnEntity(
    val trnId: String? = null,
    val coachId: String? = null,
    val clientId: String? = null,
    val clientFullName: String? = null,
    val startsAt: String? = null,
    val durationMin: String? = null,
    val type: String? = null,
    val planNotes: String? = null,
    val resultNotes: String? = null,
    val status: String? = null,
    val paymentStatus: String? = null
) {
    constructor(model: DskTrn) : this(
        trnId = model.trnId.takeIf { it != DskTrnId.NONE }?.asString(),
        coachId = model.coachId.takeIf { it != DskCoachId.NONE }?.asString(),
        clientId = model.clientId.takeIf { it != DskClientId.NONE }?.asString(),
        clientFullName = model.clientFullName.takeIf { it.isNotBlank() },
        startsAt = model.startsAt.takeIf { it != Instant.NONE }.toString(),
        durationMin = model.durationMin.takeIf { it != Duration.ZERO }.toString(),
        type = model.type.takeIf { it != DskTrnType.NONE }?.name,
        planNotes = model.planNotes.takeIf { it.isNotBlank() },
        resultNotes = model.resultNotes.takeIf { it.isNotBlank() },
        status = model.status.takeIf { it != DskTrnStatus.NONE }?.name,
        paymentStatus = model.paymentStatus.takeIf { it != DskTrnPaymentStatus.NONE }?.name,
    )

    fun toInternal(): DskTrn = DskTrn(
        trnId = trnId?.let { DskTrnId(it) } ?: DskTrnId.NONE,
        coachId = coachId?.let { DskCoachId(it) } ?: DskCoachId.NONE,
        clientId = clientId?.let { DskClientId(it) } ?: DskClientId.NONE,
        clientFullName = clientFullName ?: "",
        startsAt = startsAt?.let { Instant.parse(it) } ?: Instant.NONE,
        durationMin = durationMin?.let { Duration.parse(it) } ?: Duration.ZERO,
        type = type?.let { DskTrnType.valueOf(it) } ?: DskTrnType.NONE,
        planNotes = planNotes ?: "",
        resultNotes = resultNotes ?: "",
        status = status?.let { DskTrnStatus.valueOf(it) } ?: DskTrnStatus.NONE,
        paymentStatus = paymentStatus?.let { DskTrnPaymentStatus.valueOf(it) } ?: DskTrnPaymentStatus.NONE,
    )

}