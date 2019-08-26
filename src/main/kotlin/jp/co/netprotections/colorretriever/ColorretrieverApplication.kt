package jp.co.netprotections.colorretriever

import jp.co.netprotections.colorretriever.config.ColorWebClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(
		"jp.co.netprotections.colorretriever.controller",
		"jp.co.netprotections.colorretriever.config",
		"jp.co.netprotections.colorretriever.data",
		"jp.co.netprotections.colorretriever.repository"
)
class ColorretrieverApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<ColorretrieverApplication>(*args)

	ColorWebClient().consume()
}