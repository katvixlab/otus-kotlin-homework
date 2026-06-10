package repo

import models.DskError
import models.DskTrn

sealed interface IDbTrnResponse : IDbResponse<DskTrn>

data class DbTrnResponseOk(
    val data: DskTrn
): IDbTrnResponse

data class DbTrnResponseErr(
    val errors: List<DskError>
): IDbTrnResponse {
    constructor(error: DskError): this(errors = listOf(error))
}

data class DbTrnResponseErrWithData(
    val data: DskTrn,
    val errors: List<DskError>,
) : IDbTrnResponse {
    constructor(data: DskTrn): this(data = data, errors = emptyList())
}

