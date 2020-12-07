package br.com.felipeomachado.entities.request

import java.time.LocalDateTime

class ConverterTransactionRequest (
    val userId: Long,
    val sourceCurrency: String,
    val sourceValue: Double,
    val targetCurrency: String

    ) {

    var targetValue: Double = 0.0
    var conversionRate : Double = 0.0
    val dateTime: LocalDateTime = LocalDateTime.now()
}

