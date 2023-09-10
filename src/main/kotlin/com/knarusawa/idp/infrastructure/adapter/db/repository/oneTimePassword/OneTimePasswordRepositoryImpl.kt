package com.knarusawa.idp.infrastructure.adapter.db.repository.oneTimePassword

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.knarusawa.idp.domain.model.oneTimePassword.OneTimePassword
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import org.springframework.stereotype.Repository

@Repository
class OneTimePasswordRepositoryImpl(
  private val amazonDynamoDB: AmazonDynamoDB
) : OnetimePasswordRepository {
  companion object {
    const val TABLE_NAME = "OneTimePasswordRecord"
  }

  override suspend fun save(oneTimePassword: OneTimePassword) {
    val itemValues = mutableMapOf<String, AttributeValue>()
    itemValues["user_id"] = AttributeValue().withS(oneTimePassword.userId.toString())
    itemValues["code"] = AttributeValue().withS(oneTimePassword.code.toString())
    itemValues["expired"] = AttributeValue().withS(oneTimePassword.expired.toString())

    val request = PutItemRequest()
      .withTableName(TABLE_NAME)
      .withItem(itemValues)

    amazonDynamoDB.putItem(request)
  }

  override suspend fun findByUserId(userId: UserId): OneTimePassword? {
    val keyToGet = mutableMapOf<String, AttributeValue>()
    keyToGet["user_id"] = AttributeValue().withS(userId.toString())

    val request = GetItemRequest()
      .withTableName(TABLE_NAME)
      .withKey(keyToGet)

    val result = amazonDynamoDB.getItem(request)

    if (result.item.isEmpty())
      return null

    return OneTimePassword.of(
      userId = result.item["user_id"]?.s.toString(),
      code = result.item["code"]?.s.toString(),
      expired = result.item["expired"]?.s.toString()
    )
  }

  override suspend fun deleteByUserId(userId: UserId) {
    val keyToGet = mutableMapOf<String, AttributeValue>()
    keyToGet["user_id"] = AttributeValue().withS(userId.toString())

    val request = DeleteItemRequest()
      .withTableName(TABLE_NAME)
      .withKey(keyToGet)
    amazonDynamoDB.deleteItem(request)
  }
}