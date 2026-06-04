import models.DskTrnPaymentStatus
import org.jetbrains.exposed.sql.Table

fun Table.EnumTrnPaymentStatus(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = TrnTable.SqlField.PAYMENT,
    fromDb = { value -> DskTrnPaymentStatus.valueOf(value as String) },
    toDb = { PGEnum(TrnTable.SqlField.PAYMENT, it) }
)