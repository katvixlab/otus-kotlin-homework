package ws

interface IDskWsSession {
    suspend fun <T> send(obj: T)
    companion object {
        val NONE = object : IDskWsSession {
            override suspend fun <T> send(obj: T) {

            }
        }
    }
}
