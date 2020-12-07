package br.com.felipeomachado.repositories

import br.com.felipeomachado.db.ConverterTransactions
import br.com.felipeomachado.entities.ConverterTransaction
import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.format.DateTimeFormatter

class ConverterTransactionRepository {
    fun save(converterTransactionRequest: ConverterTransactionRequest): Long {
        var id: Long = 0

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(ConverterTransactions)

            val converterTransactionInserted = ConverterTransaction.new {
                userId = converterTransactionRequest.userId
                sourceCurrency = converterTransactionRequest.sourceCurrency
                sourceValue = converterTransactionRequest.sourceValue
                targetCurrency = converterTransactionRequest.targetCurrency
                targetValue = converterTransactionRequest.targetValue
                conversionRate = converterTransactionRequest.conversionRate
                dateTime = converterTransactionRequest.dateTime
            }

            id =  converterTransactionInserted.id.value
        }

        return id
    }

    fun findAllByUser(userId: Long): List<ConverterTransactionResponse> {
        return  transaction { ConverterTransaction.find{ ConverterTransactions.userId eq userId}.map { mapToConverterTransactionToResponseDto(it) } }
    }

    private fun mapToConverterTransactionToResponseDto(it: ConverterTransaction) = ConverterTransactionResponse(
        transactionId =it.id.value,
        userId = it.userId,
        sourceCurrency = it.sourceCurrency,
        sourceValue = it.sourceValue,
        targetCurrency = it.targetCurrency,
        targetValue = it.targetValue,
        conversionRate = it.conversionRate,
        dateTime = it.dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
        )
}
