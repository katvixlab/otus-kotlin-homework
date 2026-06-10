import models.DskTrn
import models.DskTrnId
import org.junit.Test
import repo.DbTrnIdRequest
import repo.DbTrnResponseErr
import repo.DbTrnResponseOk
import repo.DbTrnsResponseOk
import repo.IRepoTrn
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoTrnReadTest {
    abstract val repo: IRepoTrn
    protected open val readSuccess = initObjects.first()

    @Test
    fun readSuccess() = runRepoTest {
        val resul = repo.readTrn(DbTrnIdRequest(readSuccess.trnId))
        assertIs<DbTrnResponseOk>(resul)
        assertEquals(readSuccess, resul.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readTrn(DbTrnIdRequest(notFound))
        assertIs<DbTrnResponseErr>(result)
    }

    companion object : BaseGenerateTrn("read") {
        override val initObjects: List<DskTrn> = listOf(
            createTestModel("read")
        )

        val notFound = DskTrnId(UUID.fromString("12345678-0000-0000-0000-0000000000"))
    }
}