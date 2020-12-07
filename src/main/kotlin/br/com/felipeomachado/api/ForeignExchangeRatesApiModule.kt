package br.com.felipeomachado.api

import org.koin.dsl.module

// Koin module
val foreignExchangeRatesApiModuleModule = module {
    single { ForeignExchangeRatesApi() }
}