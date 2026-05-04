package ok.coachdesk.app.v1.ws

import apiV1Mapper
import com.fasterxml.jackson.module.kotlin.readValue
import fromTransport
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import models.DskCommand
import ok.coachdesk.app.DskAppSettings
import ok.coachdesk.app.base.KtorWsSessionV1
import ru.otus.kotlin.coachdesk.api.v1.models.IRequest
import ru.otus.kotlin.coachdesk.app.common.controllerHelper
import toTransport
import kotlin.reflect.KClass

private val TrnWsV1: KClass<*> = WebSocketSession::wsHandlerV1::class
suspend fun WebSocketSession.wsHandlerV1(appSettings: DskAppSettings) = with(KtorWsSessionV1(this)) {
    val sessionRepo = appSettings.corSettings.wsSession
    sessionRepo.add(this)

    appSettings.controllerHelper(
        {
            command = DskCommand.INIT
            wsSession = this@with
        },
        {
            outgoing.send(Frame.Text(apiV1Mapper.writeValueAsString(toTransport())))
        },
        TrnWsV1,
        "wsV1-init"
    )


    incoming.receiveAsFlow().mapNotNull {
        val frameText = it as? Frame.Text ?: return@mapNotNull
        try {
            appSettings.controllerHelper(
                {
                    fromTransport(apiV1Mapper.readValue<IRequest>(frameText.readText()))
                    wsSession = this@with
                },
                {
                    val result = apiV1Mapper.writeValueAsString(toTransport())
                    outgoing.send(Frame.Text(result))
                },
                TrnWsV1,
                "wsV1-handler"
            )
        } catch (_: ClosedReceiveChannelException) {
            sessionRepo.remove(this@with)
        }finally {
            appSettings.controllerHelper(
                {
                    command = DskCommand.FINISH
                    wsSession = this@with
                },
                { },
                TrnWsV1,
                "wsV1-finish"
            )
            sessionRepo.remove(this@with)
        }
    }.collect()
}