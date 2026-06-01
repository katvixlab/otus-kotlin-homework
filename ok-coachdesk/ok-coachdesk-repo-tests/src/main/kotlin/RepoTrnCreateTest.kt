import kotlinx.datetime.Instant
import models.DskClientId
import models.DskCoachId
import models.DskTrn
import models.DskTrnId
import repo.DbTrnRequest
import repo.DbTrnResponseOk
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration

abstract class RepoTrnCreateTest {
    abstract val repo: IRepoTrnInitializable
    protected open val uuid = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000000"))

    private val createTrn = DskTrn(
        trnId = DskTrnId(UUID.fromString("12345678-1111-1234-0000-0000000000")),
        coachId = DskCoachId(UUID.fromString("12345678-1111-1234-0000-0000000000")),
        clientId = DskClientId(UUID.fromString("12345678-1111-1234-0000-000000000000")),
        clientFullName = "Ivan Ivanov",
        startsAt = Instant.parse("2026-07-08T10:15:30+01:00"),
        durationMin = Duration.parse("90m"),
        planNotes = "plane",
        resultNotes = "create",
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createTrn(DbTrnRequest(createTrn))
        val expected = createTrn.copy()
        assertIs<DbTrnResponseOk>(result)
        assertEquals(uuid, result.data.trnId)
        assertEquals(expected.clientFullName, result.data.clientFullName)
        assertEquals(expected.startsAt, result.data.startsAt)
        assertEquals(expected.durationMin, result.data.durationMin)
        assertEquals(expected.planNotes, result.data.planNotes)
        assertEquals(expected.resultNotes, result.data.resultNotes)
    }

    companion object : BaseGenerateTrn("create"){
        override val initObjects: List<DskTrn> = emptyList()
    }

}