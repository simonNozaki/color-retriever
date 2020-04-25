package jp.co.netprotections.colorretriever.controller

import jp.co.netprotections.colorretriever.data.Color
import jp.co.netprotections.colorretriever.extension.invokeValidate
import jp.co.netprotections.colorretriever.repository.ColorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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
                .check { isValidCode(req.code) }
                .buildMono { colorRepository.setColorByCode(req) }
    }

    /**
     * 名前で色を検索します。
     * @param name 色の名前
     * @return
     */
    @RequestMapping(value= ["/color/{name}"],produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getColorByName(@PathVariable("name") name: String): Mono<Color> {

        return invokeValidate(name)
                .check { name.isValidName() }
                .buildMono { colorRepository.getColorByKey(name) }
    }

    /**
     * 16進数コードで色を検索します。
     */
    @RequestMapping(value= ["/color/{code}"],produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getColorByCode(@PathVariable("code") code: String): Mono<Color> {

        return invokeValidate(code)
                .check { isValidCode(code) }
                .buildMono { colorRepository.getColorByCode(code) }
    }

    private fun isValidCode(code: String): Boolean {
        if(code.length != 6) return false

        return true
    }

    private fun String.isValidName(): Boolean {
        val REGEX_HEX = "[a-f0-9]+"
        // 空文字チェック
        if(this.isBlank()) return false

        // 桁数チェック
        if(this.chars().count().toInt() > 40) return false

        // 半角英数字パターン
        val regex = Regex(pattern = REGEX_HEX)
        if (!regex.matches(this)) return false// 空文字チェック

        return true
    }
}