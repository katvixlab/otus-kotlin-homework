import models.DskTrnStatus
import org.jetbrains.exposed.sql.Table

fun Table.EnumTrnStatus(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = TrnTable.SqlField.STATUS,
    fromDb = { value -> DskTrnStatus.valueOf(value as String) },
    toDb = { PGEnum(TrnTable.SqlField.STATUS, it) }
)