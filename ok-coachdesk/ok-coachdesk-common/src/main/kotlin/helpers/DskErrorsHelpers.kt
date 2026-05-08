package helpers

import models.DskError

fun Throwable.asDskError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: localizedMessage ?: "Unknown error"
) = DskError(
    code = code,
    group = group,
    message = message,
    exception = this,
)