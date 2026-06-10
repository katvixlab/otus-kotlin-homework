package models

@JvmInline
value class DskTrnLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = DskTrnLock("")
    }
}