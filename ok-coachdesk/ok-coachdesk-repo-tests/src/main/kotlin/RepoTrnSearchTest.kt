import kotlinx.datetime.Instant
import models.DskTrn
import models.DskTrnId
import models.DskTrnPaymentStatus
import models.DskTrnStatus
import models.DskTrnType
import org.junit.Test
import repo.DbTrnFilterRequest
import repo.DbTrnsResponseOk
import repo.IRepoTrn
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoTrnSearchTest {
    abstract val repo: IRepoTrn

    @Test
    fun searchAll() = runRepoTest {
        val result = repo.searchTrn(DbTrnFilterRequest())
        assertIs<DbTrnsResponseOk>(result)
        assertEquals(initObjects.toSet(), result.data.toSet())
    }

    @Test
    fun searchByClientFullName() = runRepoTest {
        val result = repo.searchTrn(DbTrnFilterRequest(clientFullName = "client"))
        assertIs<DbTrnsResponseOk>(result)
        assertEquals(setOf(clientMatch), result.data.toSet())
    }

    @Test
    fun searchByStartsAt() = runRepoTest {
        val result = repo.searchTrn(DbTrnFilterRequest(startsAt = startsAtMatch.startsAt))
        assertIs<DbTrnsResponseOk>(result)
        assertEquals(setOf(startsAtMatch), result.data.toSet())
    }

    @Test
    fun searchByType() = runRepoTest {
        val result = repo.searchTrn(DbTrnFilterRequest(type = DskTrnType.CARDIO))
        assertIs<DbTrnsResponseOk>(result)
        assertEquals(setOf(typeMatch), result.data.toSet())
    }

    @Test
    fun searchByStatus() = runRepoTest {
        val result = repo.searchTrn(DbTrnFilterRequest(status = DskTrnStatus.DONE))
        assertIs<DbTrnsResponseOk>(result)
        assertEquals(setOf(statusMatch), result.data.toSet())
    }

    @Test
    fun searchByPaymentStatus() = runRepoTest {
        val result = repo.searchTrn(DbTrnFilterRequest(paymentStatus = DskTrnPaymentStatus.PAID))
        assertIs<DbTrnsResponseOk>(result)
        assertEquals(setOf(paymentStatusMatch), result.data.toSet())
    }

    companion object : BaseGenerateTrn("search") {
        private fun trnId(suffix: String) =
            DskTrnId(UUID.fromString("12345678-1111-1234-0000-$suffix"))

        val clientMatch = createTestModel("client").copy(
            trnId = trnId("000000000001"),
            clientFullName = "Ivan Clientov",
        )
        val startsAtMatch = createTestModel("starts-at").copy(
            trnId = trnId("000000000002"),
            startsAt = Instant.parse("2026-08-08T10:15:30+01:00"),
        )
        val typeMatch = createTestModel("type", type = DskTrnType.CARDIO).copy(
            trnId = trnId("000000000003"),
        )
        val statusMatch = createTestModel("status", status = DskTrnStatus.DONE).copy(
            trnId = trnId("000000000004"),
        )
        val paymentStatusMatch = createTestModel(
            "payment-status",
            paymentStatus = DskTrnPaymentStatus.PAID,
        ).copy(
            trnId = trnId("000000000005"),
        )

        override val initObjects: List<DskTrn> = listOf(
            clientMatch,
            startsAtMatch,
            typeMatch,
            statusMatch,
            paymentStatusMatch,
        )
    }
}
