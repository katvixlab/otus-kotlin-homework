package models

import NONE
import kotlinx.datetime.Instant
import kotlin.time.Duration

data class DskTrn(
    /* Id тренировки */
    var trnId: DskTrnId = DskTrnId.NONE,

    /* Id Тренера */
    var coachId: DskCoachId = DskCoachId.NONE,

    /* Id Клиента */
    var clientId: DskClientId = DskClientId.NONE,

    /* ФИО Клиента */
    var clientFullName: String = "",

    /* Дата и время начала тренировки */
    var startsAt: Instant = Instant.NONE,

    /* Продолжительность тренировки в минутах */
    var durationMin: Duration = Duration.ZERO,

    var type: DskTrnType = DskTrnType.NONE,

    /* Плана тренировки (в виде заметки) */
    var planNotes: String = "",

    /* Результат тренировки (в виде заметки) */
    var resultNotes: String = "",

    var status: DskTrnStatus = DskTrnStatus.NONE,

    var paymentStatus: DskTrnPaymentStatus = DskTrnPaymentStatus.NONE,
) {

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = DskTrn()
    }
}