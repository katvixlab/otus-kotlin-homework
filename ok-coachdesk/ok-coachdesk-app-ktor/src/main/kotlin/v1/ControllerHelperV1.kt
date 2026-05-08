package ok.coachdesk.app.v1

import fromTransport
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ok.coachdesk.app.DskAppSettings
import ru.otus.kotlin.coachdesk.api.v1.models.IRequest
import ru.otus.kotlin.coachdesk.api.v1.models.IResponse
import ru.otus.kotlin.coachdesk.app.common.controllerHelper
import toTransport
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, reified R : IResponse> ApplicationCall.processV1(
    appSettings: DskAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    getRequest = { fromTransport(receive<Q>()) },
    toResponse = { respond(toTransport()) },
    clazz = clazz,
    logId = logId,
)