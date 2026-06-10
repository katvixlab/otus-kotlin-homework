import models.DskTrn
import repo.IRepoTrn

interface IRepoTrnInitializable : IRepoTrn {
    fun save(trns: Collection<DskTrn>): Collection<DskTrn>
}