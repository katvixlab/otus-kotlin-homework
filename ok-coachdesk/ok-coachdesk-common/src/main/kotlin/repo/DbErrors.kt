package repo

import models.DskError
import models.DskTrnId

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: DskTrnId) = DbTrnResponseErr(
    DskError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "Id",
        message = "Training session with ID: $id not found."
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