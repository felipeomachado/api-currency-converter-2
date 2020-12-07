package br.com.felipeomachado.controllers

import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.repositories.converterTransactionRepositoryModule
import br.com.felipeomachado.services.converterTransactionServiceModule
import io.javalin.http.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.exposed.sql.Database
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.KoinApiExtension
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.core.component.inject

@KoinApiExtension
class ConverterTransactionControllerTest : KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(converterTransactionControllerModule, converterTransactionServiceModule, converterTransactionRepositoryModule)
    }
    private val ctx = mockk<Context>(relaxed = true)
    private val controller : ConverterTransactionController by inject()

    @Before
    fun setUp() {
        Database.connect("jdbc:h2:mem:currency_converter;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
    }

    @KoinApiExtension
    @Test
    fun `POST to create new currency converter with valid request`() {
        every { ctx.body<ConverterTransactionRequest>()} returns ConverterTransactionRequest(userId = 2, sourceCurrency = "USD", sourceValue = 2.00, targetCurrency = "BRL")
        controller.convertCurrency(ctx)
        verify { ctx.status(201) }
    }

    @KoinApiExtension
    @Test
    fun `POST to create new currency converter return error 400 for invalid source currency request`() {
        every { ctx.body<ConverterTransactionRequest>()} returns ConverterTransactionRequest(userId = 2, sourceCurrency = "ASD", sourceValue = 2.00, targetCurrency = "BRL")
        controller.convertCurrency(ctx)
        verify { ctx.status(400) }
    }

    @KoinApiExtension
    @Test
    fun `POST to create new currency converter return error 400 for invalid target currency request`() {
        every { ctx.body<ConverterTransactionRequest>()} returns ConverterTransactionRequest(userId = 2, sourceCurrency = "USD", sourceValue = 2.00, targetCurrency = "ASD")
        controller.convertCurrency(ctx)
        verify { ctx.status(400) }
    }

    @KoinApiExtension
    @Test
    fun `POST to create new currency converter return error 400 for invalid user id request`() {
        every { ctx.body<ConverterTransactionRequest>()} returns ConverterTransactionRequest(userId = -1, sourceCurrency = "USD", sourceValue = 2.00, targetCurrency = "BRL")
        controller.convertCurrency(ctx)
        verify { ctx.status(400) }
    }
}