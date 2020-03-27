package com.linecorp.sample.coffee.api.client

import com.linecorp.sample.coffee.protocol.Coffee
import retrofit2.http.GET

/**
 * Recipe of expose specific implementation.
 * Pros. Most of deserialization libraries supports.
 *       And Don't need any verbose configuration.
 * Cons. This exposes concrete response class to outside.
 */
data class CoffeeResponse(override val name: String, override val countryOfOrigin: String) : Coffee

interface CoffeeApi {
    @GET("/")
    suspend fun getCoffee(): CoffeeResponse
}
