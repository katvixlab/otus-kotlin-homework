package helpers

import DskContext
import models.DskError
import models.DskState
import ru.otus.kotlin.coachdesk.logging.common.LogLevel

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

fun DskContext.addErrors(error: Collection<DskError>) = errors.addAll(error)
fun DskContext.addError(error: DskError) = errors.add(error)

fun DskContext.fail(error: DskError) {
    addError(error)
    state = DskState.FAILED
}

fun DskContext.fail(errors: Collection<DskError>) {
    addErrors(errors)
    state = DskState.FAILED
}

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = DskError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorSystem(
    violationCode: String,
    level: LogLevel = LogLevel.ERROR,
    e: Throwable,
) = DskError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    level = level,
    exception = e,
)