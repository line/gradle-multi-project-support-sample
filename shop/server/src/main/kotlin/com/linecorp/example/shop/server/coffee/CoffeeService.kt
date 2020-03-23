package com.linecorp.example.shop.server.coffee

import com.linecorp.sample.coffee.api.client.CoffeeApi
import com.linecorp.sample.coffee.protocol.Coffee
import org.springframework.stereotype.Service

@Service
class CoffeeService(private val api: CoffeeApi) {
    suspend fun get(): Coffee = api.getCoffee()
}
