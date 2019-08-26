package jp.co.netprotections.colorretriever.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory


val logger: Logger = LoggerFactory.getLogger(AppLogger().javaClass)


fun <T> log(t: T) {

    when(t) {
        is String -> { logger.info(t) }
        else -> { logger.info(ObjectMapper().writeValueAsString(t)) }
    }
}