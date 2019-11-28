package com.linecorp.sample.coffee.api.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hibernate.validator.constraints.URL
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import javax.validation.constraints.NotBlank

@Configuration
@EnableConfigurationProperties(CoffeeApiClientProperties::class)
class CoffeeApiClientAutoConfiguration {
    @Bean
    fun coffeeApi(properties: CoffeeApiClientProperties) = Retrofit
            .Builder()
            .baseUrl(properties.baseUrl)
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .build()
            .create<CoffeeApi>()
}


@Validated
@ConstructorBinding
@ConfigurationProperties("coffee.api")
data class CoffeeApiClientProperties(
        @URL
        @NotBlank
        val baseUrl: String
)
