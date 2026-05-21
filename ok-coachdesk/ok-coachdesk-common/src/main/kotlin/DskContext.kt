import kotlinx.datetime.Instant
import models.*
import repo.IRepoTrn
import stubs.DskStubs
import ws.IDskWsSession

data class DskContext(
    var command: DskCommand = DskCommand.NONE,
    var state: DskState = DskState.NONE,
    val errors: MutableList<DskError> = mutableListOf(),

    var corSettings: DskCorSettings = DskCorSettings(),
    var workMode: DskWorkMode = DskWorkMode.PROD,
    var stubCase: DskStubs = DskStubs.NONE,
    var wsSession: IDskWsSession = IDskWsSession.NONE,

    var requestId: DskRequestId = DskRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var trnRequest: DskTrn = DskTrn(),
    var trnFilterRequest: DskTrnFilter = DskTrnFilter(),

    var trnValidating: DskTrn = DskTrn(),
    var trnFilterValidating: DskTrnFilter = DskTrnFilter(),

    var trnValidated: DskTrn = DskTrn(),
    var trnFilterValidated: DskTrnFilter = DskTrnFilter(),

    var trnRepo: IRepoTrn = IRepoTrn.NONE,
    var trnRepoRead: DskTrn = DskTrn(),
    var trnRepoPrepare: DskTrn = DskTrn(),
    var trnRepoDone: DskTrn = DskTrn(),
    var trnsRepoDone: MutableList<DskTrn> = mutableListOf(),

    var trnResponse: DskTrn = DskTrn(),
    var trnsResponse: MutableList<DskTrn> = mutableListOf(),
)