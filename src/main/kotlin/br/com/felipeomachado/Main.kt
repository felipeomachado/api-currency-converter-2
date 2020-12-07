package br.com.felipeomachado

import br.com.felipeomachado.controllers.ConverterTransactionController
import br.com.felipeomachado.controllers.converterTransactionControllerModule
import br.com.felipeomachado.db.DatabaseInitializer
import br.com.felipeomachado.repositories.converterTransactionRepositoryModule
import br.com.felipeomachado.services.converterTransactionServiceModule
import io.javalin.Javalin
import org.jetbrains.exposed.sql.Database
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Main {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Main::class.java)
        private const val h2ConnectionString = "jdbc:h2:mem:currency_converter;DB_CLOSE_DELAY=-1;"

        @KoinApiExtension
        @JvmStatic
        fun main(args: Array<String>) {

            startKoin {
                printLogger()
                modules(converterTransactionControllerModule, converterTransactionServiceModule, converterTransactionRepositoryModule)
            }

            logger.info("H2 database connection string: $h2ConnectionString")
            val db = Database.connect(h2ConnectionString, driver = "org.h2.Driver")
            db.useNestedTransactions = true

            DatabaseInitializer.createSchemaAndTestData()

            Javalin.create().apply {
                exception(Exception::class.java) { e, _ -> logger.error(e.message) }
                error(404) { ctx -> ctx.json("Resource not found") }
            }
                    .get("/") { ctx -> ctx.result("Service Online") }
                    .post("/api/v1/convert") { ctx -> ConverterTransactionController().convertCurrency(ctx) }
                    .get("/api/v1/transactions/user/:user-id") { ctx ->ConverterTransactionController().listTransactionsByUser(ctx) }
                    .start(7000)
        }
    }
}