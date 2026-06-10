package repo.exception

import models.DskTrnId
import models.DskTrnLock

class RepoConcurrencyException(id: DskTrnId, expectedLock: DskTrnLock, actualLock: DskTrnLock?) : RepoTrnException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)