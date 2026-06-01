import models.DskTrn
import repo.*

class TrnRepositoryMock(
    private val invokeCreateTrn: (DbTrnRequest) -> IDbTrnResponse = { DEFAULT_TRN_SUCCESS_EMPTY_MOCK },
    private val invokeReadTrn: (DbTrnIdRequest) -> IDbTrnResponse = { DEFAULT_TRN_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateTrn: (DbTrnRequest) -> IDbTrnResponse = { DEFAULT_TRN_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteTrn: (DbTrnIdRequest) -> IDbTrnResponse = { DEFAULT_TRN_SUCCESS_EMPTY_MOCK },
    private val invokeSearchTrn: (DbTrnFilterRequest) -> IDbTrnsResponse = { DEFAULT_TRNS_SUCCESS_EMPTY_MOCK },
) : IRepoTrn {
    override suspend fun readTrn(req: DbTrnIdRequest): IDbTrnResponse {
        return invokeReadTrn(req)
    }

    override suspend fun createTrn(req: DbTrnRequest): IDbTrnResponse {
        return invokeCreateTrn(req)
    }

    override suspend fun deleteTrn(req: DbTrnIdRequest): IDbTrnResponse {
        return invokeDeleteTrn(req)
    }

    override suspend fun updateTrn(req: DbTrnRequest): IDbTrnResponse {
        return invokeUpdateTrn(req)
    }

    override suspend fun searchTrn(req: DbTrnFilterRequest): IDbTrnsResponse {
        return invokeSearchTrn(req)
    }


    companion object {
        val DEFAULT_TRN_SUCCESS_EMPTY_MOCK = DbTrnResponseOk(DskTrn())
        val DEFAULT_TRNS_SUCCESS_EMPTY_MOCK = DbTrnsResponseOk(emptyList())
    }
}