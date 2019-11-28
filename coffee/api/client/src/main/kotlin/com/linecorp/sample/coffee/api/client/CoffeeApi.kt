package com.linecorp.sample.coffee.api.client

import com.linecorp.sample.coffee.protocol.Coffee
import retrofit2.http.GET

interface CoffeeApi {
    @GET("/")
    suspend fun getCoffee(): Coffee
}
