package ok.coachdesk.app.base

import ru.otus.otuskotlin.marketplace.common.ws.IDskWsSessionRepo
import ws.IDskWsSession

class KtorWsSessionRepo: IDskWsSessionRepo {
    private val sessions: MutableSet<IDskWsSession> = mutableSetOf()
    override fun add(session: IDskWsSession) {
        sessions.add(session)
    }

    override fun clearAll() {
        sessions.clear()
    }

    override fun remove(session: IDskWsSession) {
        sessions.remove(session)
    }

    override suspend fun <T> sendAll(obj: T) {
        sessions.forEach { it.send(obj) }
    }
}
