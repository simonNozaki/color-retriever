package jp.co.netprotections.colorretriever.repository

import jp.co.netprotections.colorretriever.config.RedisClientFactory
import jp.co.netprotections.colorretriever.data.Color
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import redis.clients.jedis.Jedis
import redis.clients.jedis.util.Slowlog

@Repository
class ColorRepository {

    fun getAllColors(): Flux<Color> {
        // キーを全件取得
        val colors: Set<String> = RedisClientFactory.from().keys("*")

        // キーで検索してマップを作成、リスト化
        return when(colors.size) {
            0 ->  { mutableListOf<Color>().toFlux() }
            else -> {
                colors.map { key: String ->
                    val value: String = RedisClientFactory.from().get(key)
                    Color(key, value)
                }
                .toList()
                .toFlux()
            }
        }
    }

    fun getColorByKey(name: String): Mono<Color> {
        val exist: Boolean = RedisClientFactory.from().exists(name)

        return when(exist) {
            true -> {
                val code: String = RedisClientFactory.from().get(name)
                Color(name, code)
            }
            else -> Color(name, "")
        }.toMono()
    }

    fun setColorByCode(req: Color): Mono<Color> {
        RedisClientFactory.from().set(req.name, req.code)
        return req.toMono()
    }

    fun getSlowlogs(): Flux<Slowlog> {
        return RedisClientFactory.from().slowlogGet().toFlux()
    }

}
