import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.junit.AfterClass
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.testcontainers.containers.PostgreSQLContainer
import java.sql.DriverManager
import kotlin.test.AfterTest
import kotlin.test.Ignore


@RunWith(Enclosed::class)
class TrnRepoSqlTest {

    @Ignore
    companion object {
        private val postgres = PostgreSQLContainer("postgres:16-alpine")
        private var properties = runMigrations()

        @JvmStatic
        @AfterClass
        fun tearDown() {
            postgres.stop()
        }


        fun runMigrations(): SqlProperties {
            postgres.start()
            val connection = DriverManager.getConnection(
                postgres.jdbcUrl,
                postgres.username,
                postgres.password
            )

            val database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(JdbcConnection(connection))

            val liquibase = Liquibase("sql/db.changelog-master.sql", ClassLoaderResourceAccessor(), database)
            liquibase.update()
            connection.close()

            return SqlProperties(
                host = postgres.host,
                port = postgres.firstMappedPort,
                user = postgres.username,
                password = postgres.password,
                database = postgres.databaseName
            )
        }
    }

    class TrnRepoSqlCreateTest : RepoTrnCreateTest() {
        override val repo = TrnRepoInitialized(
            TrnRepoSql(properties),
            initObjects = initObjects,
        )

        @AfterTest
        fun tearDown() = repo.clear()
    }


    class TrnRepoSqlReadTest : RepoTrnReadTest() {
        override val repo = TrnRepoInitialized(
            TrnRepoSql(properties),
            initObjects = initObjects,
        )

        @AfterTest
        fun tearDown() = repo.clear()
    }

    class TrnRepoSqlUpdateTest : RepoTrnUpdateTest() {
        override val repo = TrnRepoInitialized(
            TrnRepoSql(properties, randomLock = { "lock" }),
            initObjects = initObjects,
        )

        @AfterTest
        fun tearDown() = repo.clear()
    }

    class TrnRepoSqlDeleteTest : RepoTrnDeleteTest() {
        override val repo = TrnRepoInitialized(
            TrnRepoSql(properties),
            initObjects = initObjects,
        )

        @AfterTest
        fun tearDown() = repo.clear()
    }

    class TrnRepoSqlSearchTest : RepoTrnSearchTest() {
        override val repo = TrnRepoInitialized(
            TrnRepoSql(properties),
            initObjects = initObjects,
        )

        @AfterTest
        fun tearDown() = repo.clear()
    }

}

private fun IRepoTrnInitializable.clear() {
    val pgRepo = (this as TrnRepoInitialized).repo as TrnRepoSql
    pgRepo.clear()
}
