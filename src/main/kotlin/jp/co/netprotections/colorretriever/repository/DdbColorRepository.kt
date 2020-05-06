package jp.co.netprotections.colorretriever.repository

import jp.co.netprotections.colorretriever.client.AwsDdbClient
import jp.co.netprotections.colorretriever.data.Color
import jp.co.netprotections.colorretriever.data.DdbColor
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

/**
 * AWS DynamoDB Colorテーブルリポジトリクラス
 */
@Repository
class DdbColorRepository {

    fun insertColor(color: Color): Mono<Color> {
        AwsDdbClient.getDynamoDBMapper().save(DdbColor(color.name,color.code))
        return color.toMono()
    }

    fun fetchColor(name: String): Mono<DdbColor> {
        // 検索結果がなければnullが返る
        val retrievedColor: DdbColor? = AwsDdbClient.getDynamoDBMapper().load(DdbColor::class.java, name)
        return when(retrievedColor) {
            null -> DdbColor("","")
            else -> retrievedColor
        }.toMono()
    }
}