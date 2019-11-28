package com.linecorp.example.shop.server

import com.linecorp.example.shop.server.coffee.CoffeeService
import com.linecorp.example.shop.server.juice.JuiceService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ShopApplication

fun main(args: Array<String>) {
    runApplication<ShopApplication>(*args)
}

@RestController
class BeverageController(
        private val coffeeService: CoffeeService,
        private val juiceService: JuiceService
) {
    @GetMapping("/coffee")
    suspend fun getCoffee() = coffeeService.get()

    @GetMapping("/juice")
    suspend fun getJuice() = juiceService.get()
}

