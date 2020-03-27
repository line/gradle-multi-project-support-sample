package com.linecorp.sample.juice.api.client

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.linecorp.sample.juice.protocol.Juice
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
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().registerTypeModule<Juice, JuiceResponse>()))
            .build()
            .create<JuiceApi>()
}

inline fun <reified INTERFACE, reified IMPLEMENTATION : INTERFACE> ObjectMapper.registerTypeModule(): ObjectMapper = registerModule(typeModule<INTERFACE, IMPLEMENTATION>())

inline fun <reified INTERFACE, reified IMPLEMENTATION : INTERFACE> typeModule() = SimpleModule("JuiceResolver", Version.unknownVersion()).apply {
    setAbstractTypes(SimpleAbstractTypeResolver().addMapping(INTERFACE::class.java, IMPLEMENTATION::class.java))
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
