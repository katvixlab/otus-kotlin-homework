package ru.otus.otuskotlin.marketplace.common.ws

import ws.IDskWsSession

interface IDskWsSessionRepo {
    fun add(session: IDskWsSession)
    fun clearAll()
    fun remove(session: IDskWsSession)
    suspend fun <K> sendAll(obj: K)

    companion object {
        val NONE = object : IDskWsSessionRepo {
            override fun add(session: IDskWsSession) {}
            override fun clearAll() {}
            override fun remove(session: IDskWsSession) {}
            override suspend fun <K> sendAll(obj: K) {}
        }
    }
}
