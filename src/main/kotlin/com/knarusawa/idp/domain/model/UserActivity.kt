package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.ActivityData
import com.knarusawa.idp.domain.value.ActivityType
import com.knarusawa.idp.domain.value.UserId
import com.knarusawa.idp.infrastructure.adapter.db.record.UserActivityRecord
import java.time.LocalDateTime

class UserActivity private constructor(
        val userId: UserId,
        val activityType: ActivityType,
        val activityData: ActivityData?,
        val timestamp: LocalDateTime
) {
    companion object {
        fun of(
                userId: UserId,
                activityType: ActivityType,
                activityData: ActivityData
        ) = UserActivity(
                userId = userId,
                activityType = activityType,
                activityData = activityData,
                timestamp = LocalDateTime.now()
        )

        fun from(userActivityRecord: UserActivityRecord) = UserActivity(
                userId = UserId(value = userActivityRecord.userId),
                activityType = ActivityType.from(userActivityRecord.activityType),
                activityData = ActivityData(userActivityRecord.activityData),
                timestamp = userActivityRecord.timestamp
        )
    }

    fun toRecord() = UserActivityRecord(
            userId = this.userId.toString(),
            activityType = this.activityType.toString(),
            activityData = this.activityData?.toString(),
            timestamp = this.timestamp,
    )
}