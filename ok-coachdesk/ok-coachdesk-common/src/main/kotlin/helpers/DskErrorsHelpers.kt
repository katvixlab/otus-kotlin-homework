package helpers

import DskContext
import models.DskError
import models.DskState

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

fun DskContext.addErrors(vararg error: DskError) = errors.addAll(error)

fun DskContext.fail(error: DskError) {
    addErrors(error)
    state = DskState.FAILED
}