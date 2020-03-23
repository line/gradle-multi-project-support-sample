package com.linecorp.example.shop.server

import com.linecorp.example.shop.server.coffee.CoffeeService
import com.linecorp.example.shop.server.juice.JuiceService
import com.linecorp.sample.coffee.protocol.Coffee
import com.linecorp.sample.juice.protocol.Juice
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ShopApplication

fun main(args: Array<String>) {
    runApplication<ShopApplication>(*args)
}

data class RootResponse(val coffee: Coffee, val juice: Juice)

@RestController
class BeverageController(
        private val coffeeService: CoffeeService,
        private val juiceService: JuiceService
) {
    @GetMapping("/")
    suspend fun getRoot() = RootResponse(coffeeService.get(), juiceService.get())

    @GetMapping("/coffee")
    suspend fun getCoffee() = coffeeService.get()

    @GetMapping("/juice")
    suspend fun getJuice() = juiceService.get()
}
