package ok.coachdesk.app.base

import apiV1ResponseSerialize
import io.ktor.websocket.*
import ru.otus.kotlin.coachdesk.api.v1.models.IResponse
import ws.IDskWsSession

data class KtorWsSessionV1(
    private val session: WebSocketSession
) : IDskWsSession {
    override suspend fun <T> send(obj: T) {
        require(obj is IResponse)
        session.send(Frame.Text(apiV1ResponseSerialize(obj)))
    }
}
