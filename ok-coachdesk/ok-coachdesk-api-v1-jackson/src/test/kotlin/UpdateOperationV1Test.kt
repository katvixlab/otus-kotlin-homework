import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class UpdateOperationV1Test {

    private val request = TrnUpdateRequest(
        requestType = "update",
        debug = trnDebugFixture,
        trn = trnRequestObjectFixture
    )

    private val response = TrnUpdateResponse(
        responseType = "update",
        errors = listOf(errorFixture),
        result = RequestResult.SUCCESS,
        trn = trnResponseObjectFixtures
    )

    @Test
    fun serializeUpdateRequest() {
        val json = apiV1RequestSerialize(request)
        assertContains(json, Regex("\"requestType\":\\s*\"update\""))
        assertContains(json, Regex("\"mode\":\\s*\"test\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"clientFullName\":\\s*\"client\""))
        assertContains(json, Regex("\"id\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeRequest`() {
        val json = apiV1RequestSerialize(request)
        val requestDeserialize = apiV1RequestDeserialize<TrnUpdateRequest>(json)
        assertEquals(requestDeserialize, request)
    }

    @Test
    fun serializeUpdateResponse() {
        val json = apiV1ResponseSerialize(response)
        assertContains(json, Regex("\"responseType\":\\s*\"update\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"code\":\\s*\"duplicated-code\""))
        assertContains(json, Regex("\"clientFullName\":\\s*\"client\""))
        assertContains(json, Regex("\"id\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeResponse`() {
        val json = apiV1ResponseSerialize(response)
        val responseDeserialize = apiV1ResponseDeserialize<TrnUpdateResponse>(json)
        assertEquals(responseDeserialize, response)
    }
}
