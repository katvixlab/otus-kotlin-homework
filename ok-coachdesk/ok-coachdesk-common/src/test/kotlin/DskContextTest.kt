import kotlinx.datetime.Instant
import models.DskClientId
import models.DskCoachId
import models.DskRequestId
import models.DskTrnId
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DskContextTest {

    val emptyId = UUID.fromString("00000000-0000-0000-0000-000000000000")
    val rndId = UUID.randomUUID()

    @Test
    fun dskRequestId() {
        val requestId = DskRequestId("100")
        assertEquals(requestId.asString(), "100")
        assertEquals(DskRequestId.NONE.asString(), "")
    }

    @Test
    fun dskCoachId() {
        val dskCoachId = DskCoachId(rndId)
        assertEquals(dskCoachId.get(), rndId)
        assertEquals(DskCoachId.NONE.get(), emptyId)
    }

    @Test
    fun instantTest(){
        val none = Instant.NONE
        val parse = Instant.parse("1970-01-01T00:00:00Z")
        assertEquals(parse, none)
    }

    @Test
    fun dskClientId(){
        val dskClientId = DskClientId(rndId)
        assertEquals(dskClientId.get(), rndId)
        assertEquals(DskClientId.NONE.get(), emptyId)
    }

    @Test
    fun dskTrnId(){
        val dskTrnId = DskTrnId(rndId)
        assertEquals(dskTrnId.get(), rndId)
        assertEquals(DskTrnId.NONE.get(), emptyId)
    }
}