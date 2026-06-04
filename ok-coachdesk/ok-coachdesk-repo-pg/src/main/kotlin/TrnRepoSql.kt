import helpers.asDskError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import repo.*
import java.util.*
import kotlin.time.Duration.Companion.minutes

class TrnRepoSql(
    properties: SqlProperties,
    private val randomUuid: () -> UUID = UUID::randomUUID,
) : IRepoTrn, IRepoTrnInitializable {
    private val trnTable = TrnTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction {
        trnTable.deleteAll()
    }

    private fun saveObj(trn: DskTrn): DskTrn = transaction(conn) {
        val trns = trnTable
            .insert { it.to(trn, randomUuid) }
            .resultedValues
            ?.map { trnTable.from(it) }
        trns?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    override fun save(trns: Collection<DskTrn>): Collection<DskTrn> = trns.map { saveObj(it) }

    override suspend fun readTrn(req: DbTrnIdRequest): IDbTrnResponse = transactionWrapper {
        val row = trnTable.selectAll().where {
            trnTable.id eq req.trnId.get()
        }.singleOrNull() ?: return@transactionWrapper errorNotFound(req.trnId)
        return@transactionWrapper DbTrnResponseOk(trnTable.from(row))
    }

    override suspend fun createTrn(req: DbTrnRequest): IDbTrnResponse = transactionWrapper {
        DbTrnResponseOk(saveObj(req.trn))
    }

    override suspend fun deleteTrn(req: DbTrnRequest): IDbTrnResponse =
        checkpoint(req.trn.trnId, req.trn.lock) {
            trnTable.deleteWhere { trnTable.id eq req.trn.trnId.get() }
            DbTrnResponseOk(req.trn)
        }

    override suspend fun updateTrn(req: DbTrnRequest): IDbTrnResponse =
        checkpoint(req.trn.trnId, req.trn.lock) {
            trnTable.updateReturning(where = { trnTable.id eq req.trn.trnId.get() }) {
                it.to(req.trn.copy(lock = DskTrnLock(randomUuid().toString())), randomUuid)
            }.singleOrNull()
                ?.let { DbTrnResponseOk(trnTable.from(it)) }
                ?: errorNotFound(req.trn.trnId)
        }

    override suspend fun searchTrn(req: DbTrnFilterRequest): IDbTrnsResponse =
        transactionWrapper({
            val query = trnTable.selectAll()

            req.clientFullName.takeIf { it.isNotBlank() }?.let { clientFullName ->
                val literal = LikePattern.ofLiteral(clientFullName.lowercase())
                val pattern = LikePattern("%${literal.pattern}%", literal.escapeChar)
                query.andWhere { trnTable.clientFullName.lowerCase() like pattern }
            }
            req.paymentStatus.takeIf { it != DskTrnPaymentStatus.NONE }?.let { paymentStatus ->
                query.andWhere { trnTable.trnPaymentStatus eq paymentStatus }
            }
            req.startsAt.takeIf { it != Instant.NONE }?.let { startsAt ->
                query.andWhere {
                    (trnTable.startsAt greaterEq startsAt.minus(5.minutes)) and
                            (trnTable.startsAt lessEq startsAt.plus(5.minutes))
                }
            }
            req.type.takeIf { it != DskTrnType.NONE }?.let { type ->
                query.andWhere { trnTable.trnType eq type }
            }
            req.status.takeIf { it != DskTrnStatus.NONE }?.let { status ->
                query.andWhere { trnTable.trnStatus eq status }
            }

            DbTrnsResponseOk(query.map { trnTable.from(it) })
        }) { DbTrnsResponseErr(it.asDskError()) }

    private suspend fun checkpoint(
        id: DskTrnId,
        lock: DskTrnLock,
        block: (DskTrn) -> IDbTrnResponse
    ): IDbTrnResponse = transactionWrapper {
        if (id == DskTrnId.NONE) return@transactionWrapper errorEmptyId

        val existTrn = trnTable.selectAll().where { trnTable.id eq id.get() }
            .singleOrNull()
            ?.let { trnTable.from(it) }

        when {
            existTrn == null -> errorNotFound(id)
            existTrn.lock != lock -> errorRepoConcurrency(existTrn, lock)
            else -> block(existTrn)
        }
    }

    private suspend inline fun <T> transactionWrapper(
        crossinline block: () -> T,
        crossinline handle: (Exception) -> T
    ): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbTrnResponse): IDbTrnResponse =
        transactionWrapper(block) { DbTrnResponseErr(it.asDskError()) }

}
