import models.DskTrnType
import org.jetbrains.exposed.sql.Table

fun Table.EnumTrnType(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = TrnTable.SqlField.TYPE,
    fromDb = { value -> DskTrnType.valueOf(value as String) },
    toDb = { PGEnum(TrnTable.SqlField.TYPE, it) }
)