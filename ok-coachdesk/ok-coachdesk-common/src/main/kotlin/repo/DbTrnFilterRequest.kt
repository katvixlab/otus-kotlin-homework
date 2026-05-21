package repo

import NONE
import kotlinx.datetime.Instant
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType

data class DbTrnFilterRequest(
    var clientFullName: String = "",
    var startsAt: Instant = Instant.NONE,
    var type: DskTrnType = DskTrnType.NONE,
    var status: DskTrnStatus = DskTrnStatus.NONE,
    var paymentStatus: DskTrnPaymentStatus = DskTrnPaymentStatus.NONE,
)
