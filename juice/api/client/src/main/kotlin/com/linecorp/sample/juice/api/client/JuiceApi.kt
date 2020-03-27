package com.linecorp.sample.juice.api.client

import com.linecorp.sample.juice.protocol.Juice
import retrofit2.http.GET

/**
 * Recipe of registering implementation mapping into converter side.
 * Please check [JuiceApiClientAutoConfiguration] also.
 *
 * Pros. Can hide this implementation class from out side of ths module.
 * Cons. Dependent on deserialization feature. jackson supports this, but others may not.
 */
internal data class JuiceResponse(override val name: String) : Juice

interface JuiceApi {
    @GET("/")
    suspend fun get(): Juice
}
