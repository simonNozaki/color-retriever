package jp.co.netprotections.colorretriever.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory


val logger: Logger = LoggerFactory.getLogger(AppLogger().javaClass)


fun <T> log(t: T) {

    if (t is String){
        logger.info(t)
    }

}