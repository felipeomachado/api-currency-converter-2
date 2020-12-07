package br.com.felipeomachado.services

import br.com.felipeomachado.api.ForeignExchangeRatesApi
import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import br.com.felipeomachado.repositories.ConverterTransactionRepository
import br.com.felipeomachado.validators.ConverterTransactionRequestValidator
import java.time.format.DateTimeFormatter

class ConverterTransactionService(private val converterTransactionRepository: ConverterTransactionRepository) {

    private val foreignExchangeRatesApi = ForeignExchangeRatesApi()

    fun convert(converterTransactionRequest: ConverterTransactionRequest): Result<ConverterTransactionResponse> {
        try {
            ConverterTransactionRequestValidator.validate(converterTransactionRequest)

            val ratesResult = foreignExchangeRatesApi.getRates(converterTransactionRequest.sourceCurrency.toUpperCase())

            ratesResult.onSuccess { value ->
                if (value != null) {
                    val conversionRate = value.rates[converterTransactionRequest.targetCurrency.toUpperCase()]
                            ?: return Result.failure(Exception("Target currency '${converterTransactionRequest.targetCurrency}' was not supported"))

                    val targetValue = conversionRate * converterTransactionRequest.sourceValue

                    converterTransactionRequest.conversionRate = conversionRate;
                    converterTransactionRequest.targetValue = targetValue

                    val id: Long = converterTransactionRepository.save(converterTransactionRequest)

                    return Result.success(
                            ConverterTransactionResponse(
                                    id,
                                    converterTransactionRequest.userId,
                                    converterTransactionRequest.sourceCurrency,
                                    converterTransactionRequest.sourceValue,
                                    converterTransactionRequest.targetCurrency,
                                    converterTransactionRequest.targetValue,
                                    converterTransactionRequest.conversionRate,
                                    converterTransactionRequest.dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
                            ))
                }
            }

            throw Exception("Source currency '${converterTransactionRequest.sourceCurrency}' was not supported")
        }catch (exception: Exception) {
            return Result.failure(exception)
        }
    }

    fun getAllTransactionsByUser(userId: Long): List<ConverterTransactionResponse> {
        return converterTransactionRepository.findAllByUser((userId))
    }

}
