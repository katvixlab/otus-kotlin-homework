class TrnRepoInMemoryCreateTest : RepoTrnCreateTest() {
    override val repo = TrnRepoInitialized(
        TrnRepoInMemory(randomUuid = { uuid.asString() }),
        initObjects = initObjects,
    )
}

class TrnRepoInMemoryReadTest : RepoTrnReadTest() {
    override val repo = TrnRepoInitialized(
        TrnRepoInMemory(),
        initObjects = initObjects,
    )
}

class TrnRepoInMemoryUpdateTest : RepoTrnUpdateTest() {
    override val repo = TrnRepoInitialized(
        TrnRepoInMemory(),
        initObjects = initObjects,
    )
}

class TrnRepoInMemoryDeleteTest : RepoTrnDeleteTest() {
    override val repo = TrnRepoInitialized(
        TrnRepoInMemory(),
        initObjects = initObjects,
    )
}

class TrnRepoInMemorySearchTest : RepoTrnSearchTest() {
    override val repo = TrnRepoInitialized(
        TrnRepoInMemory(),
        initObjects = initObjects,
    )
}
