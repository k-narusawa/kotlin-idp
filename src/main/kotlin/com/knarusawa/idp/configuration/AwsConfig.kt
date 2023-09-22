package com.knarusawa.idp.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AwsConfig {
  companion object {
    private const val REGION = "ap-northeast-1"
    private const val ACCESS_KEY = "AccessKey"
    private const val SECRET_KEY = "SecretKey"
    private const val DYNAMO_ENDPOINT = "http://localhost:8000"
    private const val SES_ENDPOINT = "http://localhost:8005"
  }

  @Bean
  fun awsCredentialsProvider(): AWSStaticCredentialsProvider {
    val credentials = BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)
    return AWSStaticCredentialsProvider(credentials)
  }

  @Primary
  @Bean
  fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB): DynamoDBMapper {
    return DynamoDBMapper(amazonDynamoDB, DynamoDBMapperConfig.DEFAULT)
  }

  @Bean
  fun dynamoDB(): AmazonDynamoDB {
    val awsCredentialsProvider = awsCredentialsProvider()
    val endpointConfiguration = AwsClientBuilder.EndpointConfiguration(DYNAMO_ENDPOINT, REGION)
    return AmazonDynamoDBClientBuilder.standard()
      .withCredentials(awsCredentialsProvider)
      .withEndpointConfiguration(endpointConfiguration)
      .build()
  }

  @Bean
  fun simpleEmailService(): AmazonSimpleEmailService {
    val awsCredentialsProvider = awsCredentialsProvider()
    val endpointConfiguration = AwsClientBuilder.EndpointConfiguration(SES_ENDPOINT, REGION)
    return AmazonSimpleEmailServiceClientBuilder.standard()
      .withCredentials(awsCredentialsProvider)
      .withEndpointConfiguration(endpointConfiguration)
      .build()
  }
}