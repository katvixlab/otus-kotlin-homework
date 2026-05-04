package ru.otus.kotlin.coachdesk.app.ktor.stub

import DskCorSettings
import io.ktor.client.call.*
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*

import io.ktor.server.testing.*
import kotlinx.coroutines.withTimeout
import ok.coachdesk.app.DskAppSettings
import ok.coachdesk.app.moduleApp
import org.junit.Test
import ru.otus.kotlin.coachdesk.api.v1.models.IRequest
import ru.otus.kotlin.coachdesk.api.v1.models.IResponse
import ru.otus.kotlin.coachdesk.api.v1.models.RequestResult
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnCreateResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnDeleteResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnInitResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnReadResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnSearchResponse
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateRequest
import ru.otus.kotlin.coachdesk.api.v1.models.TrnUpdateResponse
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V1TrnStubWsTest {

    @Test
    fun create() {
        val request = TrnCreateRequest(
            requestType = "create")
        v1WsTestApplication<IResponse>(request){
            assertEquals(RequestResult.SUCCESS, it.result)
        }
    }



    private inline fun <reified T> v1WsTestApplication(
        request: IRequest,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        application { moduleApp(DskAppSettings(corSettings = DskCorSettings())) }
        val client = createClient {
            install(WebSockets) {
                contentConverter = JacksonWebsocketContentConverter()
            }
        }

        client.webSocket("/v1/ws") {
            withTimeout(3000) {
                val response = receiveDeserialized<IResponse>() as T
                assertIs<TrnInitResponse>(response)
            }
            sendSerialized(request)
            withTimeout(3000) {
                val response = receiveDeserialized<IResponse>() as T
                assertBlock(response)
            }
        }
    }


}