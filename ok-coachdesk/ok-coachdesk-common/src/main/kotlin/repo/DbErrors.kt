package repo

import models.DskError
import models.DskTrn
import models.DskTrnId
import models.DskTrnLock
import repo.exception.RepoConcurrencyException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: DskTrnId) = DbTrnResponseErr(
    DskError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "Id",
        message = "Training session with ID: $id not found."
    )
)

fun errorEmptyLock(id: DskTrnId) = DbTrnResponseErr(
    DskError(
        code = "${ERROR_GROUP_REPO}-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Ad ${id.asString()} is empty that is not admitted"
    )
)


val errorEmptyId = DbTrnResponseErr(
    DskError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "Id",
        message = "Id must not be empty."
    )
)

val errorsDbRepoNotImplemented = DbTrnsResponseErr(
    DskError(
        code = "$ERROR_GROUP_REPO-db-repo-not-implemented",
        group = ERROR_GROUP_REPO,
        field = "DbRepo",
        message = "DbRepo must be implemented."
    )
)

val errorDbRepoNotImplemented = DbTrnResponseErr(
    errorsDbRepoNotImplemented.errors.first()
)

fun errorRepoConcurrency(
    oldTrn: DskTrn,
    expectedLock: DskTrnLock,
    exception: Exception = RepoConcurrencyException(
        id = oldTrn.trnId,
        expectedLock = expectedLock,
        actualLock = oldTrn.lock,
    ),
) = DbTrnResponseErrWithData(
    data = oldTrn,
    errors = listOf(
        DskError(
            code = "${ERROR_GROUP_REPO}-concurrency",
            group = ERROR_GROUP_REPO,
            field = "lock",
            message = "The object with ID ${oldTrn.trnId.asString()} has been changed concurrently by another user or process",
            exception = exception,
        )
    )
)