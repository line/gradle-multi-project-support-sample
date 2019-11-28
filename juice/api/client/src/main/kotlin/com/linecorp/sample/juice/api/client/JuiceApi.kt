package com.linecorp.sample.juice.api.client

import com.linecorp.sample.juice.protocol.Juice
import retrofit2.http.GET

interface JuiceApi {
    @GET("/")
    suspend fun get(): Juice
}
