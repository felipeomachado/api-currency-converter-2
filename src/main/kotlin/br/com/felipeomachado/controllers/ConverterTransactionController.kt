package br.com.felipeomachado.controllers

import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import br.com.felipeomachado.repositories.ConverterTransactionRepository
import br.com.felipeomachado.services.ConverterTransactionService
import io.javalin.http.Context
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@KoinApiExtension
class ConverterTransactionController: KoinComponent {
    private val converterTransactionService: ConverterTransactionService by inject()

    fun convertCurrency(ctx: Context) {
        val result = converterTransactionService.convert(ctx.body<ConverterTransactionRequest>())

        result.onSuccess {
            result.getOrNull()?.let { it1 -> ctx.status(201).json(it1) }
        }
        result.onFailure {
            result.exceptionOrNull()?.message?.let { it1 -> ctx.status(400).json(it1) }
        }
    }

    fun listTransactionsByUser(ctx: Context)  {
        try {
            val userId = ctx.pathParam("user-id").toLong()
            ctx.json(converterTransactionService.getAllTransactionsByUser(userId))
        }catch (exception: Exception) {
            ctx.status(400).json("Invalid User Id")
        }

    }
}