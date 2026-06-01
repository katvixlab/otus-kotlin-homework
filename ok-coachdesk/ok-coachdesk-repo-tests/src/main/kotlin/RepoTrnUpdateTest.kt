import models.DskTrn
import models.DskTrnId
import org.junit.Test
import repo.DbTrnRequest
import repo.DbTrnResponseErr
import repo.DbTrnResponseOk
import repo.IRepoTrn
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoTrnUpdateTest {
    abstract val repo: IRepoTrn
    protected open val updateSuccess = initObjects.first()

    @Test
    fun updateSuccess() = runRepoTest {
        val expected = updateSuccess.copy(clientFullName = "updated client", resultNotes = "updated result")
        val result = repo.updateTrn(DbTrnRequest(expected))
        assertIs<DbTrnResponseOk>(result)
        assertEquals(expected, result.data)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateTrn(DbTrnRequest(updateSuccess.copy(trnId = notFound)))
        assertIs<DbTrnResponseErr>(result)
    }

    @Test
    fun updateEmptyId() = runRepoTest {
        val result = repo.updateTrn(DbTrnRequest(updateSuccess.copy(trnId = DskTrnId.NONE)))
        assertIs<DbTrnResponseErr>(result)
    }

    companion object : BaseGenerateTrn("update") {
        override val initObjects: List<DskTrn> = listOf(
            createTestModel("update")
        )

        val notFound = DskTrnId(UUID.fromString("12345678-0000-0000-0000-0000000000"))
    }
}
