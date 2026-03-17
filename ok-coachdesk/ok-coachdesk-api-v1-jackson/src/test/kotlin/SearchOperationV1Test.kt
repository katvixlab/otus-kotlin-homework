import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SearchOperationV1Test {

    private val request = TrnSearchRequest(
        requestType = "search",
        debug = trnDebugFixture,
        trnFilter = trnSearchFilterFixture
    )

    private val response = TrnSearchResponse(
        responseType = "search",
        errors = listOf(errorFixture),
        result = RequestResult.SUCCESS,
        trns = listOf(trnResponseObjectFixtures)
    )

    @Test
    fun serializeSearchRequest() {
        val json = apiV1RequestSerialize(request)
        assertContains(json, Regex("\"requestType\":\\s*\"search\""))
        assertContains(json, Regex("\"mode\":\\s*\"test\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"clientFullName\":\\s*\"client\""))
        assertContains(json, Regex("\"paymentStatus\":\\s*\"paid\""))
    }

    @Test
    fun `round-trip SerializeRequest`() {
        val json = apiV1RequestSerialize(request)
        val requestDeserialize = apiV1RequestDeserialize<TrnSearchRequest>(json)
        assertEquals(requestDeserialize, request)
    }

    @Test
    fun serializeSearchResponse() {
        val json = apiV1ResponseSerialize(response)
        assertContains(json, Regex("\"responseType\":\\s*\"search\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"code\":\\s*\"duplicated-code\""))
        assertContains(json, Regex("\"clientFullName\":\\s*\"client\""))
        assertContains(json, Regex("\"trnId\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeResponse`() {
        val json = apiV1ResponseSerialize(response)
        val responseDeserialize = apiV1ResponseDeserialize<TrnSearchResponse>(json)
        assertEquals(responseDeserialize, response)
    }
}
