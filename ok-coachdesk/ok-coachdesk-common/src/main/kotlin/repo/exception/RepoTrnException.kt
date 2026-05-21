package repo.exception

import models.DskTrnId

open class RepoTrnException(
    val trnId: DskTrnId,
    message: String,
): RepoException("Id: $trnId, $message")