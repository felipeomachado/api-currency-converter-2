package br.com.felipeomachado.repositories

import org.koin.dsl.module

// Koin module
val converterTransactionRepositoryModule = module {
    single { ConverterTransactionRepository() }


}