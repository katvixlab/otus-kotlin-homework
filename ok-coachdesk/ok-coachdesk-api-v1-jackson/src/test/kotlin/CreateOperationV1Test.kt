import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateResponse
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals


class CreateOperationV1Test {

    private val request = TrnCreateRequest(
        requestType = "create",
        debug = trnDebugFixture,
        trn = trnRequestObjectFixture
    )

    private val response = TrnCreateResponse(
        responseType = "create",
        errors = listOf(errorFixture),
        result = RequestResult.SUCCESS,
        trn = trnResponseObjectFixtures
    )

    @Test
    fun serializeCreateRequest() {
        val json = apiV1RequestSerialize(request)
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"mode\":\\s*\"test\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"clientFullName\":\\s*\"client\""))
        assertContains(json, Regex("\"durationMin\":\\s*\"90\""))
        assertContains(json, Regex("\"planNotes\":\\s*\"plan\""))
    }

    @Test
    fun `round-trip SerializeRequest`(){
        val json = apiV1RequestSerialize(request)
        val requestDeserialize = apiV1RequestDeserialize<TrnCreateRequest>(json)
        assertEquals(requestDeserialize, request)
    }

    @Test
    fun serializeCreateResponse() {
        val json = apiV1ResponseSerialize(response)
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"result\":\\s*\"success\""))
        assertContains(json, Regex("\"code\":\\s*\"duplicated-code\""))
        assertContains(json, Regex("\"clientFullName\":\\s*\"client\""))
        assertContains(json, Regex("\"durationMin\":\\s*\"90\""))
        assertContains(json, Regex("\"planNotes\":\\s*\"plan\""))
        assertContains(json, Regex("\"trnId\":\\s*\"$trnIdFixture\""))
    }

    @Test
    fun `round-trip SerializeResponse`(){
        val json = apiV1ResponseSerialize(response)
        val responseDeserialize = apiV1ResponseDeserialize<TrnCreateResponse>(json)
        assertEquals(responseDeserialize, response)
    }
}