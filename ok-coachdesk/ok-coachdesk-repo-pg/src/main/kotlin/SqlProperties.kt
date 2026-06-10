data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "password",
    val database: String = "coachdsk",
    val schema: String = "public",
    val table: String = "trn",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}