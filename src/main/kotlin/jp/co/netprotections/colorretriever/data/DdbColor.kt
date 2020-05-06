package jp.co.netprotections.colorretriever.data

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

/**
 * AWS DynamoDB用マッピングデータクラス
 */
@DynamoDBTable(tableName = "color")
data class DdbColor(
    @DynamoDBHashKey(attributeName = "name")
    var name: String,
    @DynamoDBAttribute(attributeName = "code")
    var code: String
)