package ok.coachdesk.app.controllers

import io.ktor.server.application.ApplicationCall
import ok.coachdesk.app.DskAppSettings
import ok.coachdesk.app.v1.processV1
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
import kotlin.reflect.KClass

val trnCreate: KClass<*> = ApplicationCall::createTrn::class
suspend fun ApplicationCall.createTrn(appSettings: DskAppSettings) =
    processV1<TrnCreateRequest, TrnCreateResponse>(appSettings, trnCreate, "create-trn")

val trnRead: KClass<*> = ApplicationCall::readTrn::class
suspend fun ApplicationCall.readTrn(appSettings: DskAppSettings) =
    processV1<TrnReadRequest, TrnReadResponse>(appSettings, trnRead, "read-trn")

val trnUpdate: KClass<*> = ApplicationCall::updateTrn::class
suspend fun ApplicationCall.updateTrn(appSettings: DskAppSettings) =
    processV1<TrnUpdateRequest, TrnUpdateResponse>(appSettings, trnUpdate, "update-trn")

val trnDelete: KClass<*> = ApplicationCall::deleteTrn::class
suspend fun ApplicationCall.deleteTrn(appSettings: DskAppSettings) =
    processV1<TrnDeleteRequest, TrnDeleteResponse>(appSettings, trnDelete, "delete-trn")

val trnSearch: KClass<*> = ApplicationCall::searchTrn::class
suspend fun ApplicationCall.searchTrn(appSettings: DskAppSettings) =
    processV1<TrnSearchRequest, TrnSearchResponse>(appSettings, trnSearch, "search-trn")