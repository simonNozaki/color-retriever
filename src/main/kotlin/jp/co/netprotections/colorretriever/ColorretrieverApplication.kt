package jp.co.netprotections.colorretriever

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class ColorretrieverApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<ColorretrieverApplication>(*args)
}
