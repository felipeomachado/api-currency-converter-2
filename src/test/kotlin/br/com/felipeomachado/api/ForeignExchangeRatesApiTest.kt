package br.com.felipeomachado.api

import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertTrue


class ForeignExchangeRatesApiTest : KoinTest {
    private val foreignExchangeRatesApi by inject<ForeignExchangeRatesApi>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(foreignExchangeRatesApiModuleModule)
    }

    @Test
    fun `should get success on call the external api to request the rates when is a valid currency`() {
        val result = foreignExchangeRatesApi.getRates("USD")
        assertTrue(result.isSuccess)
    }

    @Test
    fun `should get failure on call the external api to request the rates when is a invalid currency`() {
        val result = foreignExchangeRatesApi.getRates("ASD")
        assertTrue(result.isFailure)
    }
}