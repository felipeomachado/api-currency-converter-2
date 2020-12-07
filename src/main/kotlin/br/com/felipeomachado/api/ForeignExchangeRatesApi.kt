package br.com.felipeomachado.api

import br.com.felipeomachado.api.response.ForeignExchangeRatesApiErrorResponse
import br.com.felipeomachado.api.response.ForeignExchangeRatesApiSuccessResponse
import br.com.felipeomachado.api.services.ForeignExchangeRatesApiService
import br.com.felipeomachado.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForeignExchangeRatesApi {
    companion object {
        const val URL_API = "https://api.exchangeratesapi.io"
    }

    fun getRates(currency: String): Result<ForeignExchangeRatesApiSuccessResponse?> {
        val retrofitClient = NetworkUtils.getRetrofitInstance(URL_API)

        val response = retrofitClient.create(ForeignExchangeRatesApiService::class.java).getRates(currency).execute()

        return if ( response.isSuccessful ) {
            Result.success(response.body())
        } else {
            val type = object : TypeToken<ForeignExchangeRatesApiErrorResponse>() {}.type
            var foreignExchangeRatesApiErrorResponse: ForeignExchangeRatesApiErrorResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
            Result.failure(Exception(foreignExchangeRatesApiErrorResponse.error))
        }
    }
}