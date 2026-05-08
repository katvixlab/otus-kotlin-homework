package ru.otus.kotlin.coachdesk.app.common

import DskContext
import helpers.asDskError
import kotlinx.datetime.Clock
import models.DskCommand
import models.DskState
import ru.otus.kotlin.coachdesk.api.log.mapper.toLog
import kotlin.reflect.KClass

suspend inline fun <T> IDskAppSettings.controllerHelper(
    crossinline getRequest: suspend DskContext.() -> Unit,
    crossinline toResponse: suspend DskContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = DskContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId),
            e = e,
        )
        ctx.state = DskState.FAILED
        ctx.errors.add(e.asDskError())
        processor.exec(ctx)
        if (ctx.command == DskCommand.NONE) {
            ctx.command = DskCommand.READ
        }
        ctx.toResponse()
    }
}


