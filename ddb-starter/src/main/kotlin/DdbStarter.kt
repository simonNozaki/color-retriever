import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Table
import com.amazonaws.services.dynamodbv2.model.*


private val basicAwsCredentials: ProfileCredentialsProvider = ProfileCredentialsProvider("snozaki-private")

private val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
    .withCredentials(basicAwsCredentials)
    .withEndpointConfiguration(EndpointConfiguration("http://localhost:8000", "ap-northeast-1"))
    .build()

private val dynamoDB: DynamoDB = DynamoDB(client)

private val tableName = "color"

fun main(args: Array<String>) {

    try {
        println("Attempting to create table; please wait...")
        val table: Table = dynamoDB.createTable(
            tableName,
            listOf(KeySchemaElement("name", KeyType.HASH)),  // Partition key
            listOf(AttributeDefinition("name", ScalarAttributeType.N)),
            ProvisionedThroughput(10L, 10L)
        )
        table.waitForActive()
        println("Success.  Table status: " + table.description.tableStatus)
    } catch (e: Exception) {
        System.err.println("Unable to create table: ")
        System.err.println(e.message)
        e.printStackTrace()
    }
}
