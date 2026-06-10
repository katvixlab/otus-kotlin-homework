package models

import java.util.UUID

@JvmInline
value class DskClientId(private val id: UUID) {
    fun get() = id
    fun asString() = id.toString()

    companion object {
        val NONE = DskClientId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
    }

    constructor(id: String) : this(UUID.fromString(id))
}