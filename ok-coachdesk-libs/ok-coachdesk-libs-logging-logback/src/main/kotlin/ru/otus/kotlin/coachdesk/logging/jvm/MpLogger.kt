package ru.otus.kotlin.coachdesk.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otus.kotlin.coachdesk.logging.common.IDskLogWrapper


import kotlin.reflect.KClass

fun dskLoggerLogback(logger: Logger): IDskLogWrapper = DskLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun dskLoggerLogback(clazz: KClass<*>): IDskLogWrapper = dskLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun dskLoggerLogback(loggerId: String): IDskLogWrapper = dskLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
