package br.com.felipeomachado.controllers

import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val converterTransactionControllerModule = module {
    single { ConverterTransactionController() }
}