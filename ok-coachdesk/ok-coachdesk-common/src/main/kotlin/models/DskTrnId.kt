package models

import java.util.UUID

class DskTrnId(private val id: UUID) {
    fun get() = id

    companion object {
        val NONE = DskTrnId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
    }
}