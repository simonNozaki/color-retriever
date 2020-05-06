package jp.co.netprotections.colorretriever.controller

import jp.co.netprotections.colorretriever.data.Color
import jp.co.netprotections.colorretriever.data.DdbColor
import jp.co.netprotections.colorretriever.extension.invokeValidate
import jp.co.netprotections.colorretriever.extension.isValidCode
import jp.co.netprotections.colorretriever.extension.isValidName
import jp.co.netprotections.colorretriever.repository.DdbColorRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * AWS DynamoDB対応の色REST Controller
 */
@RestController
class DdbColorRestController(private val ddbColorRepository: DdbColorRepository) {

    @RequestMapping(value= ["/ddb/color"],produces=[MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun insertColor(@RequestBody req: Color): Mono<Color> {
        return invokeValidate(req.code)
                .check { req.name.isValidName() }
                .check { req.code.isValidCode() }
                .buildMono { ddbColorRepository.insertColor(req) }
    }

    @RequestMapping(value = ["/ddb/color/{name}"],produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun fetchColor(@PathVariable("name") name: String): Mono<DdbColor> {
        return invokeValidate(name)
                .check { name.isValidName() }
                .buildMono { ddbColorRepository.fetchColor(name) }
    }
}