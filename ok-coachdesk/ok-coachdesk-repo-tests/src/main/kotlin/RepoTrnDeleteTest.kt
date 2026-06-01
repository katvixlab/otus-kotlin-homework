import models.DskTrn
import models.DskTrnId
import org.junit.Test
import repo.DbTrnIdRequest
import repo.DbTrnResponseErr
import repo.DbTrnResponseOk
import repo.IRepoTrn
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoTrnDeleteTest {
    abstract val repo: IRepoTrn
    protected open val deleteSuccess = initObjects.first()

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteTrn(DbTrnIdRequest(deleteSuccess.trnId))
        assertIs<DbTrnResponseOk>(result)
        assertEquals(deleteSuccess, result.data)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.deleteTrn(DbTrnIdRequest(notFound))
        assertIs<DbTrnResponseErr>(result)
    }

    @Test
    fun deleteEmptyId() = runRepoTest {
        val result = repo.deleteTrn(DbTrnIdRequest(DskTrnId.NONE))
        assertIs<DbTrnResponseErr>(result)
    }

    companion object : BaseGenerateTrn("delete") {
        override val initObjects: List<DskTrn> = listOf(
            createTestModel("delete")
        )

        val notFound = DskTrnId(UUID.fromString("12345678-0000-0000-0000-0000000000"))
    }
}
