package com.linecorp.sample.coffee.api.server

import com.linecorp.sample.coffee.protocol.Coffee
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.kotlin.core.publisher.toMono

data class CoffeeResponse(override val name: String, override val countryOfOrigin: String) : Coffee

@SpringBootApplication
class CoffeeApplication {
    @Bean
    fun handler() = nest(path("/"), router {
        GET("") {
            ServerResponse.ok().body(CoffeeResponse("coffee", "Jamaica").toMono())
        }
    })
}

fun main(args: Array<String>) {
    runApplication<CoffeeApplication>(*args)
}
