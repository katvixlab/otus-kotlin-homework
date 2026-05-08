package ok.coachdesk.app.v1

import io.ktor.server.routing.*
import ok.coachdesk.app.DskAppSettings
import ok.coachdesk.app.controllers.*

fun Route.v1Trn(appSettings: DskAppSettings) {
    route("trn") {
        post("create") {
            call.createTrn(appSettings)
        }
        post("read") {
            call.readTrn(appSettings)
        }
        post("update") {
            call.updateTrn(appSettings)
        }
        post("delete") {
            call.deleteTrn(appSettings)
        }
        post("search") {
            call.searchTrn(appSettings)
        }
    }
}