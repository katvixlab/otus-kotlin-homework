package ok.coachdesk.app

import apiV1Mapper
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.*
import ok.coachdesk.app.plugins.initAppSettings
import ok.coachdesk.app.v1.v1Trn
import org.slf4j.event.Level

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.moduleApp(
    appSettings: DskAppSettings = initAppSettings()
) {

    install(CORS) {
        allowHost("localhost:8080")
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
    }


    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CallLogging) {
        level = Level.INFO
    }
    routing {
        route("v1") {
            swaggerUI(path = "swagger", swaggerFile = "specs/specs-ts-v1.yml")
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiV1Mapper.serializationConfig)
                    setConfig(apiV1Mapper.deserializationConfig)
                }
            }
            v1Trn(appSettings)
        }
    }
}
