package jp.co.netprotections.colorretriever.client

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

/**
 * Redisクライアントファクトリクラス。
 */
class RedisClientFactory {

    companion object {

        private val jedisPool: JedisPool = JedisPool()

        fun from(): Jedis {
            return jedisPool.resource
        }
    }
}