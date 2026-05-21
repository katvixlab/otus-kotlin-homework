import models.DskTrn

class TrnRepoInitialized(
    private val repo: IRepoTrnInitializable,
    initObjects: Collection<DskTrn> = emptyList()
) : IRepoTrnInitializable by repo {
    val initializedObjs: List<DskTrn> = save(initObjects).toList()
}