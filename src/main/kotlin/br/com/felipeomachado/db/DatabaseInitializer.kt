package br.com.felipeomachado.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object DatabaseInitializer {
    private val logger: Logger = LoggerFactory.getLogger(DatabaseInitializer::class.java)

    fun createSchemaAndTestData() {
        logger.info("Creating/Updating schema")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(ConverterTransactions)
        }

    }
}