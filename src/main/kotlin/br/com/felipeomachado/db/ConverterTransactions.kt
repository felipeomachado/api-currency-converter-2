package br.com.felipeomachado.db

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object ConverterTransactions : LongIdTable("converterTransactions") {
    val userId = long("user_id").index()
    val sourceCurrency = varchar("sourceCurrency", 3)
    val sourceValue = double("sourceValue")
    val targetCurrency = varchar("targetCurrency", 3)
    val targetValue = double("targetValue")
    val conversionRate = double("conversionRate")
    val dateTime = datetime(("dateTime"))

    override val primaryKey = PrimaryKey(id, name = "pk_converter_transactions")
}