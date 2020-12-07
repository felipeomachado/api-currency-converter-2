package br.com.felipeomachado.services

import org.koin.dsl.module

// Koin module
val converterTransactionServiceModule = module {
    single { ConverterTransactionService(get()) }
}