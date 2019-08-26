package jp.co.netprotections.colorretriever.repository

import jp.co.netprotections.colorretriever.data.Color
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.util.function.Predicate

@Repository
class ColorRepository {

    val sets: List<Color>

    init {
        sets = listOf(
                Color("sea grean", "00ac97"),
                Color("topaz", "e9bc00"),
                Color("cyan", "00a1e9")
        )
    }

    fun getAllColors(): Flux<Color> {
        return Flux.fromIterable(sets)
    }

    fun getColorByCode(code: String): Mono<Color> {
        return Flux.fromIterable(sets).filter{ color ->  color.code == code }.toMono()
    }

}
