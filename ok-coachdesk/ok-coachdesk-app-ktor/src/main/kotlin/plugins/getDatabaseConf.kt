package ok.coachdesk.app.plugins

import SqlProperties
import TrnRepoInMemory
import TrnRepoSql
import io.ktor.server.application.*
import ok.coachdesk.app.configs.ConfigPaths
import ok.coachdesk.app.configs.PostgresConfig
import repo.IRepoTrn
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConf(type: DskDbType): IRepoTrn {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

enum class DskDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.initInMemory(): IRepoTrn {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return TrnRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}

fun Application.initPostgres(): IRepoTrn {
    val config = PostgresConfig(environment.config)
    return TrnRepoSql(
        properties = SqlProperties(
            host = config.host,
            port = config.port,
            user = config.user,
            password = config.password,
            schema = config.schema,
            database = config.database,
        ),
    )
}