import kotlinx.datetime.Instant
import models.*
import stubs.DskStubs
import ws.IDskWsSession

data class DskContext(
    var command: DskCommand = DskCommand.NONE,
    var state: DskState = DskState.NONE,
    val errors: MutableList<DskError> = mutableListOf(),

    var workMode: DskWorkMode = DskWorkMode.PROD,
    var stubCase: DskStubs = DskStubs.NONE,
    var wsSession: IDskWsSession = IDskWsSession.NONE,

    var requestId: DskRequestId = DskRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var dskRequest: DskTrn = DskTrn(),
    var dskTrnFilter: DskTrnFilter = DskTrnFilter(),

    var dskResponse: DskTrn = DskTrn(),
    var dsksResponse: MutableList<DskTrn> = mutableListOf(),
)