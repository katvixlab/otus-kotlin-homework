package models

import NONE
import kotlinx.datetime.Instant

/**
 * Набор фильтров для поиска
 *
 * @param clientFullName ФИО Клиента
 * @param startsAt Дата начала тренировки
 * @param type 
 * @param status 
 * @param paymentStatus 
 */


data class DskTrnFilter (

    /* ФИО Клиента */
    var clientFullName: String = "",

    /* Дата начала тренировки */
    var startsAt: Instant = Instant.NONE,

    var type: DskTrnType = DskTrnType.NONE,

    var status: DskTrnStatus = DskTrnStatus.NONE,

    var paymentStatus: DskTrnPaymentStatus = DskTrnPaymentStatus.NONE,
)

