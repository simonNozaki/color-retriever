package jp.co.netprotections.colorretriever.controller

import jp.co.netprotections.colorretriever.data.Color
import jp.co.netprotections.colorretriever.extension.invokeValidate
import jp.co.netprotections.colorretriever.repository.ColorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * 色検索REST Controller
 */
@RestController
class ColorRetrievingRestController(@Autowired private val colorRepository: ColorRepository) {

    @RequestMapping(value="/color",produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getColorList(): Flux<Color> {
        return colorRepository.getAllColors()
    }

    @RequestMapping(value="/color/{code}",produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getColorByCode(@PathVariable("code") code: String): Mono<Color> {

        return invokeValidate(code)
                .check { isValidCode(code) }
                .buildMono { colorRepository.getColorByCode(code) }
    }

    private fun isValidCode(code: String): Boolean {
        if(code.length != 6) return false

        return true
    }
}