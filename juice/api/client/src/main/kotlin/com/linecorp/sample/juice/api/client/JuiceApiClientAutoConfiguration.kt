package com.linecorp.sample.juice.api.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import retrofit2.http.Url
import javax.validation.constraints.NotBlank

@Configuration
@EnableConfigurationProperties(JuiceApiClientProperties::class)
class JuiceApiClientAutoConfiguration {
    @Bean
    fun juiceApi(properties: JuiceApiClientProperties) = Retrofit
            .Builder()
            .client(OkHttpClient()
                    .newBuilder()
                    .addInterceptor {
                        val request = it.request()
                                .newBuilder()
                                .addHeader("User-Agent", properties.userAgent)
                                .build()
                        it.proceed(request)
                    }
                    .build())
            .baseUrl(properties.baseUrl)
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .build()
            .create<JuiceApi>()
}

@Validated
@ConstructorBinding
@ConfigurationProperties("juice.api")
data class JuiceApiClientProperties(
        @Url
        @NotBlank
        val baseUrl: String,
        @NotBlank
        val userAgent: String
)
