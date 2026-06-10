package models

import java.util.UUID

@JvmInline
value class DskTrnId(private val id: UUID) {
    fun get() = id
    fun asString(): String = id.toString()

    companion object {
        val NONE = DskTrnId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
    }

    constructor(id: String) : this(UUID.fromString(id))
}