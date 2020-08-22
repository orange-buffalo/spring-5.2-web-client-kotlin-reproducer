package com.example.demo

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class ApiConsumer(
        @Value("\${reproducer.api-url}") private val apiUrl: String
) {
    suspend fun getApiResponse(): String {
        val webClient = WebClient.create(apiUrl)

        val response = webClient.get()
                .uri("/resource")
                .exchange().awaitFirst()

        // inspect response, like response.statusCode(); maybe return different result based on response attributes

        return response.bodyToMono(String::class.java).awaitFirst()
    }
}
