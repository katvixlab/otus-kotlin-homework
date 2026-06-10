import models.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.duration
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.util.UUID

class TrnTable(tableName: String) : Table(tableName) {
    val id = uuid("id")
    var coachId = uuid(SqlField.COACH_ID)
    var clientId = uuid(SqlField.CLIENT_ID)
    var clientFullName = text(SqlField.CLIENT_FULLNAME)
    var startsAt = timestamp(SqlField.START_AT)
    var durationMin = duration(SqlField.DURATION)
    var planNotes = text(SqlField.PLAN_NOTES)
    var resultNotes = text(SqlField.RESULT_NOTES)
    var trnType = EnumTrnType(SqlField.TYPE)
    var trnStatus = EnumTrnStatus(SqlField.STATUS)
    var trnPaymentStatus = EnumTrnPaymentStatus(SqlField.PAYMENT)
    var lock = text(SqlField.LOCK)

    fun from(res: ResultRow) = DskTrn(
        trnId = DskTrnId(res[id]),
        coachId = DskCoachId(res[coachId]),
        clientId = DskClientId(res[clientId]),
        clientFullName = res[clientFullName],
        startsAt = res[startsAt],
        durationMin = res[durationMin],
        planNotes = res[planNotes],
        resultNotes = res[resultNotes],
        type = res[trnType],
        status = res[trnStatus],
        paymentStatus = res[trnPaymentStatus],
        lock = DskTrnLock(res[lock])
    )

    fun UpdateBuilder<*>.to(trn: DskTrn, randomUuid: () -> UUID, randomLock: () -> String) {
        this[id] = trn.trnId.takeIf { it != DskTrnId.NONE }?.get() ?: randomUuid()
        this[coachId] = trn.coachId.takeIf { it != DskCoachId.NONE }?.get() ?: randomUuid()
        this[clientId] = trn.clientId.takeIf { it != DskClientId.NONE }?.get() ?: randomUuid()
        this[clientFullName] = trn.clientFullName
        this[startsAt] = trn.startsAt
        this[durationMin] = trn.durationMin
        this[planNotes] = trn.planNotes
        this[resultNotes] = trn.resultNotes
        this[trnType] = trn.type
        this[trnStatus] = trn.status
        this[trnPaymentStatus] = trn.paymentStatus
        this[lock] = trn.lock.takeIf { it != DskTrnLock.NONE }?.asString() ?: randomLock()
    }

    object SqlField {
        const val COACH_ID = "coach_id"
        const val CLIENT_ID = "client_id"
        const val CLIENT_FULLNAME = "client_full_name"
        const val START_AT = "start_at"
        const val DURATION = "duration"
        const val PLAN_NOTES = "plan_notes"
        const val RESULT_NOTES = "result_notes"
        const val LOCK = "lock"
        const val TYPE = "trn_type"
        const val STATUS = "trn_status"
        const val PAYMENT = "trn_payment"
    }
}