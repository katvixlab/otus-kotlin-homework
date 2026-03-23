import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class DeleteOperationV1Test {

    private val request = TrnDeleteRequest(
        requestType = "delete",
        debug = trnDebugFixture,
        trn = idTrnFixture
    )

    private val response = TrnDeleteResponse(
        responseType = "delete",
        errors = listOf(errorFixture),
        result = RequestResult.SUCCESS,
        trnId = trnIdFixture
    )

    @Test
    fun serializeDeleteRequest() {
        val json = apiV1RequestSerialize(request)
        assertContains(json, Regex("\"requestType\":\\s*\"delete\""))
        assertContains(json, Regex("\"mode\":\\s*\"test\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"trnId\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeRequest`() {
        val json = apiV1RequestSerialize(request)
        val requestDeserialize = apiV1RequestDeserialize<TrnDeleteRequest>(json)
        assertEquals(requestDeserialize, request)
    }

    @Test
    fun serializeDeleteResponse() {
        val json = apiV1ResponseSerialize(response)
        assertContains(json, Regex("\"responseType\":\\s*\"delete\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"code\":\\s*\"duplicated-code\""))
        assertContains(json, Regex("\"trnId\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeResponse`() {
        val json = apiV1ResponseSerialize(response)
        val responseDeserialize = apiV1ResponseDeserialize<TrnDeleteResponse>(json)
        assertEquals(responseDeserialize, response)
    }
}
