package br.com.felipeomachado.services

import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import br.com.felipeomachado.repositories.ConverterTransactionRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue


class ConverterTransactionServiceTest  {
    @RelaxedMockK
    lateinit var repository : ConverterTransactionRepository

    lateinit var converterTransactionService : ConverterTransactionService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        converterTransactionService = ConverterTransactionService(repository)
    }

    @Test
    fun `should convert currency`() {
        val userId = 1L
        val sourceCurrency = "USD"
        val sourceValue = 1.00
        val targetCurrency = "BRL"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isSuccess)
        assertEquals("Target Value was not correct", sourceValue * result.getOrNull()?.conversionRate!!,
                result.getOrNull()?.targetValue!!, 0.0)
    }

    @Test
    fun `should return error when trying to convert an source currency that does not supported`() {
        val userId = 1L
        val sourceCurrency = "ASD"
        val sourceValue = 1.00
        val targetCurrency = "BRL"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.message, "Source currency '${sourceCurrency}' was not supported")
    }

    @Test
    fun `should return error when trying to convert an target currency that does not supported`() {
        val userId = 1L
        val sourceCurrency = "USD"
        val sourceValue = 1.00
        val targetCurrency = "ASD"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.message, "Target currency '${targetCurrency}' was not supported")
    }

    @Test
    fun `should get all converter transaction by user`() {
        val userId = 5L

        every { repository.findAllByUser(userId) } returns listOf(
                ConverterTransactionResponse(1, 5, "BRL", 2.0, "USD", 10.0, 5.0, "2020-12-04T14:30:50"))

        val transactionList: List<ConverterTransactionResponse> =  converterTransactionService.getAllTransactionsByUser(userId)

        println(transactionList)

        assertTrue(transactionList.isNotEmpty())
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}