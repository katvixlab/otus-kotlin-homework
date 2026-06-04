import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class TrnRepoInMemoryTest {
    class TrnRepoInMemoryCreateTest : RepoTrnCreateTest() {
        override val repo = TrnRepoInitialized(
            TrnRepoInMemory(randomId = { uuid.asString() }),
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
            TrnRepoInMemory(randomLock = { "lock" }),
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
}