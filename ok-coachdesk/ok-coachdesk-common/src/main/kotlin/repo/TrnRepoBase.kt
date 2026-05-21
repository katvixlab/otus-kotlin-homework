package repo

import helpers.errorSystem

abstract class TrnRepoBase: IRepoTrn{

    protected suspend fun tryTrnMethod(block: suspend () -> IDbTrnResponse) = try{
        block()
    }
    catch (e: Throwable){
        DbTrnResponseErr(errorSystem(violationCode = "methodException", e = e))
    }

    protected suspend fun tryTrnsMethod(block: suspend () -> IDbTrnsResponse) = try {
        block()
    }
    catch (e: Throwable){
        DbTrnsResponseErr(errorSystem(violationCode = "methodException", e = e))
    }
}
