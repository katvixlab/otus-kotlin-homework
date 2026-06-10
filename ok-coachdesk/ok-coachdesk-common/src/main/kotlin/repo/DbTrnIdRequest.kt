package repo

import models.DskTrn
import models.DskTrnId

data class DbTrnIdRequest(
    val trnId: DskTrnId
){
    constructor(ad: DskTrn): this(ad.trnId)
}