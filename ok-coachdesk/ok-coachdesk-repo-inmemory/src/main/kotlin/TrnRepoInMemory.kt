import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Instant
import models.*
import repo.*
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TrnRepoInMemory(
    ttl: Duration = 10.minutes,
    val randomUuid: () -> String = { UUID.randomUUID().toString() }
) : TrnRepoBase(), IRepoTrn, IRepoTrnInitializable {

    private val mutex = Mutex()
    private val cache = Cache.Builder<String, TrnEntity>()
        .expireAfterAccess(ttl)
        .build()

    override suspend fun readTrn(req: DbTrnIdRequest) = tryTrnMethod {
        val id = req.trnId.takeIf { it != DskTrnId.NONE }?.asString()
            ?: return@tryTrnMethod errorEmptyId
        mutex.withLock {
            cache.get(id)?.let {
                DbTrnResponseOk(it.toInternal())
            } ?: errorNotFound(req.trnId)
        }
    }

    override suspend fun createTrn(req: DbTrnRequest): IDbTrnResponse = tryTrnMethod {
        val id = randomUuid()
        val dskTrn = req.trn.copy(trnId = DskTrnId(id))
        val trnEntity = TrnEntity(dskTrn)
        mutex.withLock {
            cache.put(id, trnEntity)
        }
        DbTrnResponseOk(dskTrn)
    }

    override suspend fun deleteTrn(req: DbTrnIdRequest) = tryTrnMethod {
        val id = req.trnId.takeIf { it != DskTrnId.NONE } ?: return@tryTrnMethod errorEmptyId
        mutex.withLock {
            val trn = cache.get(id.asString())?.toInternal()
            if (trn == null) {
                errorNotFound(id)
            } else {
                cache.invalidate(id.asString())
                DbTrnResponseOk(trn)
            }
        }
    }

    override suspend fun updateTrn(req: DbTrnRequest) = tryTrnMethod {
        val dskTrn = req.trn
        val id = dskTrn.trnId.takeIf { it != DskTrnId.NONE } ?: return@tryTrnMethod errorEmptyId
        mutex.withLock {
            val existTrn = cache.get(id.asString())?.toInternal()
            if (existTrn == null) {
                errorNotFound(id)
            } else {
                val newTrn = TrnEntity(dskTrn.copy())
                cache.put(id.asString(), newTrn)
                DbTrnResponseOk(newTrn.toInternal())
            }
        }
    }

    override suspend fun searchTrn(req: DbTrnFilterRequest): IDbTrnsResponse = tryTrnsMethod {
        val result = cache.asMap().asSequence()
            .filter { obj ->
                req.clientFullName.takeIf { it.isNotBlank() }?.let {
                    it == obj.value.clientFullName
                } ?: true
            }
            .filter { obj ->
                req.paymentStatus.takeIf { it != DskTrnPaymentStatus.NONE }?.let {
                    obj.value.paymentStatus == it.name
                } ?: true
            }
            .filter { obj ->
                req.startsAt.takeIf { it != Instant.NONE }?.let {
                    val instant = obj.value.startsAt?.let { input -> Instant.parse(input) } ?: return@let true
                    instant > it.plus(5.minutes) && instant < it.minus(5.minutes)
                } ?: true
            }
            .filter { obj ->
                req.type.takeIf { it != DskTrnType.NONE }?.let {
                    obj.value.type == it.name
                } ?: true
            }
            .filter { obj ->
                req.status.takeIf { it != DskTrnStatus.NONE }?.let {
                    obj.value.status == it.name
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbTrnsResponseOk(result)
    }

    override fun save(trns: Collection<DskTrn>) = trns.map { trn ->
        val trnEntity = TrnEntity(trn)
        require(trnEntity.trnId != null) { "trnId must be not null" }
        cache.put(trnEntity.trnId, trnEntity)
        trn
    }

}