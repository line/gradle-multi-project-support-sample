package com.linecorp.example.shop.server.juice

import com.linecorp.sample.juice.api.client.JuiceApi
import org.springframework.stereotype.Service

@Service
class JuiceService(private val api: JuiceApi) {
    suspend fun get() = api.get()
}
