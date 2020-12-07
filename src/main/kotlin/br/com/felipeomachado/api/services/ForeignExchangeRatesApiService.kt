package br.com.felipeomachado.api.services

import br.com.felipeomachado.api.response.ForeignExchangeRatesApiSuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForeignExchangeRatesApiService {

    @GET("latest?")
    fun getRates(@Query("base") currency: String) : Call<ForeignExchangeRatesApiSuccessResponse>
}