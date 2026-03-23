import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ReadOperationV1Test {

    private val request = TrnReadRequest(
        requestType = "read",
        debug = trnDebugFixture,
        trn = idTrnFixture
    )

    private val response = TrnReadResponse(
        responseType = "read",
        errors = listOf(errorFixture),
        result = RequestResult.SUCCESS,
        trn = trnResponseObjectFixtures
    )

    @Test
    fun serializeReadRequest() {
        val json = apiV1RequestSerialize(request)
        assertContains(json, Regex("\"requestType\":\\s*\"read\""))
        assertContains(json, Regex("\"mode\":\\s*\"test\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"trnId\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeRequest`() {
        val json = apiV1RequestSerialize(request)
        val requestDeserialize = apiV1RequestDeserialize<TrnReadRequest>(json)
        assertEquals(requestDeserialize, request)
    }

    @Test
    fun serializeReadResponse() {
        val json = apiV1ResponseSerialize(response)
        assertContains(json, Regex("\"responseType\":\\s*\"read\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"code\":\\s*\"duplicated-code\""))
        assertContains(json, Regex("\"clientFullName\":\\s*\"client\""))
        assertContains(json, Regex("\"trnId\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeResponse`() {
        val json = apiV1ResponseSerialize(response)
        val responseDeserialize = apiV1ResponseDeserialize<TrnReadResponse>(json)
        assertEquals(responseDeserialize, response)
    }
}
