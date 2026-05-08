package ru.otus.kotlin.coachdesk.app.ktor.stub

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*

import io.ktor.server.testing.*
import ok.coachdesk.app.DskAppSettings
import ok.coachdesk.app.moduleApp
import org.junit.Test
import ru.otus.kotlin.coachdesk.api.v1.models.IRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateResponse
import kotlin.test.assertEquals

class V1TrnStubApiTest {

    @Test
    fun create() = v1RestTestApplication(
        path = "create",
        request = TrnCreateRequest(
            requestType = "create",
        )
    ) { response ->
        val body = response.body<TrnCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345678-1111-1234-0000-000000000000", body.trn?.trnId.toString())
    }

    @Test
    fun read() = v1RestTestApplication(
        path = "read",
        request = TrnReadRequest(
            requestType = "read",
        )
    ) { response ->
        val body = response.body<TrnReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345678-1111-1234-0000-000000000000", body.trn?.trnId.toString())
    }

    @Test
    fun update() = v1RestTestApplication(
        path = "update",
        request = TrnUpdateRequest(
            requestType = "update",
        )
    ) { response ->
        val body = response.body<TrnUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345678-1111-1234-0000-000000000000", body.trn?.trnId.toString())
    }

    @Test
    fun delete() = v1RestTestApplication(
        path = "delete",
        request = TrnDeleteRequest(
            requestType = "delete",
        )
    ) { response ->
        val body = response.body<TrnDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("12345678-1111-1234-0000-000000000000", body.trnId.toString())
    }

    @Test
    fun search() = v1RestTestApplication(
        path = "search",
        request = TrnSearchRequest(
            requestType = "search",
        )
    ) { response ->
        val body = response.body<TrnSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals(6, body.trns?.size)
        assertEquals("12345678-1111-1234-0000-000000000001", body.trns?.first()?.trnId.toString())
    }


    private fun v1RestTestApplication(
        path: String,
        request: IRequest,
        function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { moduleApp(DskAppSettings()) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    enable(SerializationFeature.INDENT_OUTPUT)
                    writerWithDefaultPrettyPrinter()
                }
            }
        }
        val responses = client.post("/v1/trn/$path") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(responses)
    }



}