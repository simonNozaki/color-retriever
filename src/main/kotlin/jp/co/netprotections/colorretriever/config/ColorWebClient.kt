package jp.co.netprotections.colorretriever.config

import jp.co.netprotections.colorretriever.data.Color
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ColorWebClient {

    private val webClient: WebClient = WebClient.create("http://localhost:8080")

    fun consume() {
        val colorFlux: Flux<Color> = webClient.get()
                .uri("/color")
                .retrieve()
                .bodyToFlux(Color::class.java)
                .log()

        val colorMono: Mono<Color> = webClient.get()
                .uri("/color/{code}", "00a1e9")
                .retrieve()
                .bodyToMono(Color::class.java)
                .log()

        colorFlux.subscribe{
            log(it)
        }

        colorMono.subscribe{
            log(it)
        }
    }
}