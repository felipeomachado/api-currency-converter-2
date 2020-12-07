package br.com.felipeomachado.validators

import br.com.felipeomachado.entities.request.ConverterTransactionRequest

class ConverterTransactionRequestValidator {
    companion object {
        fun validate(converterTransactionRequest: ConverterTransactionRequest) {
            if(converterTransactionRequest.userId <= 0) {
                throw Exception("Invalid User Id")
            }

            if(converterTransactionRequest.sourceCurrency.trim().isEmpty()) {
                throw Exception("Invalid Source Currency")
            }

            if(converterTransactionRequest.targetCurrency.trim().isEmpty()) {
                throw Exception("Invalid Target Currency")
            }
        }
    }
}