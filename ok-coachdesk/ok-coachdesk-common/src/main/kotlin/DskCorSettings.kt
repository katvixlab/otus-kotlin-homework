import repo.IRepoTrn
import ru.otus.kotlin.coachdesk.logging.common.DskLoggerProvider
import ru.otus.otuskotlin.marketplace.common.ws.IDskWsSessionRepo

class DskCorSettings(
    val loggerProvider: DskLoggerProvider = DskLoggerProvider(),
    val wsSession: IDskWsSessionRepo = IDskWsSessionRepo.NONE,
    val repoStub: IRepoTrn = IRepoTrn.NONE,
    val repoTest: IRepoTrn = IRepoTrn.NONE,
    val repoProd: IRepoTrn = IRepoTrn.NONE,
) {

    companion object {
        val NONE = DskCorSettings()
    }
}