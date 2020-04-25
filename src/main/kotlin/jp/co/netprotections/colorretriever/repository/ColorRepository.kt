package jp.co.netprotections.colorretriever.repository

import jp.co.netprotections.colorretriever.config.RedisClientFactory
import jp.co.netprotections.colorretriever.data.Color
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import redis.clients.jedis.Jedis

@Repository
class ColorRepository {

    val sets: List<Color> = listOf(
            Color("sea grean", "00ac97"),
            Color("topaz", "e9bc00"),
            Color("cyan", "00a1e9")
    )

    fun getAllColors(): Flux<Color> {
        return Flux.fromIterable(sets)
    }

    fun getColorByCode(code: String): Mono<Color> {
        return Flux.fromIterable(sets).filter{ color ->  color.code == code }.toMono()
    }

    fun getColorByKey(name: String): Mono<Color> {
        val code: String? = RedisClientFactory.from().get(name)
        code?.let { return Color(name, code).toMono() }
        return Color("", "").toMono()
    }

    fun setColorByCode(req: Color): Mono<Color> {
        RedisClientFactory.from().set(req.name, req.code)
        return req.toMono()
    }

}
