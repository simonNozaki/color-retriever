package jp.co.netprotections.colorretriever.client

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Table

/**
 * DynamoDBクライアントクラス
 */
class AwsDdbClient {

    companion object {
        private val basicAwsCredentials: ProfileCredentialsProvider = ProfileCredentialsProvider("snozaki-private")

        private val amazonDynamoDB: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(basicAwsCredentials)
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-1"))
                .build()

        private val dynamoDB: DynamoDB = DynamoDB(amazonDynamoDB)

        const val TABLE_COLOR: String = "color"

        /**
         * DynamoDBのcolorテーブルを取得します。
         * @return Tableインスタンス
         */
        fun getColorTable(): Table {
            return dynamoDB.getTable(TABLE_COLOR)
        }

        fun getDynamoDBMapper(): DynamoDBMapper {
            return DynamoDBMapper(amazonDynamoDB)
        }
    }
}