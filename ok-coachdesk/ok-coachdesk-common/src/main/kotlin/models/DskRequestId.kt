package models

@JvmInline
value class DskRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = DskRequestId("")
    }
}