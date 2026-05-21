package repo

interface IRepoTrn {
    suspend fun readTrn(req: DbTrnIdRequest): IDbTrnResponse
    suspend fun createTrn(req: DbTrnRequest): IDbTrnResponse
    suspend fun deleteTrn(req: DbTrnIdRequest): IDbTrnResponse
    suspend fun updateTrn(req: DbTrnRequest): IDbTrnResponse
    suspend fun searchTrn(req: DbTrnFilterRequest): IDbTrnsResponse

    companion object {
        val NONE = object : IRepoTrn {
            override suspend fun readTrn(req: DbTrnIdRequest): IDbTrnResponse =
                errorDbRepoNotImplemented

            override suspend fun createTrn(req: DbTrnRequest): IDbTrnResponse =
                errorDbRepoNotImplemented

            override suspend fun deleteTrn(req: DbTrnIdRequest): IDbTrnResponse =
                errorDbRepoNotImplemented

            override suspend fun updateTrn(req: DbTrnRequest): IDbTrnResponse =
                errorDbRepoNotImplemented

            override suspend fun searchTrn(req: DbTrnFilterRequest): IDbTrnsResponse =
                errorsDbRepoNotImplemented

        }
    }
}