import repo.DbTrnFilterRequest
import repo.DbTrnIdRequest
import repo.DbTrnRequest
import repo.DbTrnResponseOk
import repo.DbTrnsResponseOk
import repo.IDbTrnResponse
import repo.IDbTrnsResponse
import repo.IRepoTrn

class TrnRepoStub() : IRepoTrn {
    override suspend fun readTrn(req: DbTrnIdRequest): IDbTrnResponse {
        return DbTrnResponseOk(
            data = DskStub.get()
        )
    }

    override suspend fun createTrn(req: DbTrnRequest): IDbTrnResponse {
        return DbTrnResponseOk(
            data = DskStub.get()
        )
    }

    override suspend fun deleteTrn(req: DbTrnIdRequest): IDbTrnResponse {
        return DbTrnResponseOk(
            data = DskStub.get()
        )
    }

    override suspend fun updateTrn(req: DbTrnRequest): IDbTrnResponse {
        return DbTrnResponseOk(
            data = DskStub.get()
        )
    }

    override suspend fun searchTrn(req: DbTrnFilterRequest): IDbTrnsResponse {
        return DbTrnsResponseOk(
            data = DskStub.prepareListDskTrn()
        )
    }
}