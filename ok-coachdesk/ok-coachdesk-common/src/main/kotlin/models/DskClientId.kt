package models

import java.util.UUID

@JvmInline
value class DskClientId(private val id: UUID) {
    fun get() = id

    companion object {
        val NONE = DskClientId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
    }
}