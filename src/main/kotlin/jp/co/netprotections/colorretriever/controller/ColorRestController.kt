package jp.co.netprotections.colorretriever.controller

import jp.co.netprotections.colorretriever.data.Color
import jp.co.netprotections.colorretriever.extension.ColorValidator
import jp.co.netprotections.colorretriever.extension.invokeValidate
import jp.co.netprotections.colorretriever.extension.isValidCode
import jp.co.netprotections.colorretriever.extension.isValidName
import jp.co.netprotections.colorretriever.repository.ColorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import redis.clients.jedis.util.Slowlog

/**
 * 色REST Controller
 */
@RestController
class ColorRestController(@Autowired private val colorRepository: ColorRepository) {

    /**
     * 全色を取得します。
     */
    @RequestMapping(value= ["/color"],produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getColorList(): Flux<Color> {
        return colorRepository.getAllColors()
    }

    /**
     * 色コードを新規登録します。
     * @param req 色オブジェクト
     * @return
     */
    @RequestMapping(value= ["/color"],produces=[MediaType.APPLICATION_JSON_UTF8_VALUE],method = [RequestMethod.POST])
    fun insertNewColor(@RequestBody req: Color): Mono<Color> {
        return invokeValidate(req.code)
                .check { req.code.isValidCode() }
                .check { req.name.isValidName() }
                .buildMono { colorRepository.setColorByCode(req) }
    }

    /**
     * 名前で色を検索します。
     * @param name 色の名前
     * @return
     */
    @RequestMapping(value= ["/color/{name}"],produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getColorByName(@PathVariable("name") name: String): Mono<Color> {

        ColorValidator.testName(name)
                .runCatching {
                    this.executeRepository { colorRepository.getColorByKey(name) }
                }.onSuccess { return@onSuccess }
                .onFailure { return Color("","").toMono() }


        return invokeValidate(name)
                .check { name.isValidName() }
                .buildMono { colorRepository.getColorByKey(name) }
    }

    @RequestMapping(value= ["/slowlogs"],produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getSlowlogs(): Flux<Slowlog> {
        return colorRepository.getSlowlogs()
    }
}