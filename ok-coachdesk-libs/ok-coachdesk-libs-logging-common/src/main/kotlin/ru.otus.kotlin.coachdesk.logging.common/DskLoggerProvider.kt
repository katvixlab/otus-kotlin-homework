package ru.otus.kotlin.coachdesk.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class DskLoggerProvider(
    private val provider: (String) -> IDskLogWrapper = { IDskLogWrapper.DEFAULT }
) {

    fun logger(loggerId: String): IDskLogWrapper = provider(loggerId)

    fun logger(clazz: KClass<*>): IDskLogWrapper = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    fun logger(function: KFunction<*>): IDskLogWrapper = provider(function.name)
}

