package repo

import models.DskError
import models.DskTrn

sealed interface IDbTrnsResponse : IDbResponse<List<DskTrn>>

data class DbTrnsResponseOk(
    val data: List<DskTrn>
): IDbTrnsResponse

data class DbTrnsResponseErr(
    val errors: List<DskError>
): IDbTrnsResponse{
    constructor(error: DskError): this(errors = listOf(error))
}