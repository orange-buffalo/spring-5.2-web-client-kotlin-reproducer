package com.example.demo

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@NeedsWireMock
@TestPropertySource(
        properties = [
            "reproducer.api-url=http://localhost:\${wire-mock.port}"
        ]
)
class CancelledResponseReproducer(
        @Autowired val apiConsumer: ApiConsumer
) {

    @Test
    fun `should return proper response when imperative programming with coroutines applied`() {
        stubGetRequestTo("/resource") {
            willReturn(aResponse()
                    .withStatus(200)
                    .withBody("WebClient works with coroutines"))
        }

        val response = runBlocking { apiConsumer.getApiResponse() }

        assertThat(response).isEqualTo("WebClient works with coroutines")
    }
}
