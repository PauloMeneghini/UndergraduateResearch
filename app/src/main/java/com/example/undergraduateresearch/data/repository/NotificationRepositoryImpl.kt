package com.example.undergraduateresearch.data.repository

import com.example.undergraduateresearch.data.remote.api.NotificationApi
import com.example.undergraduateresearch.data.remote.dto.toDomain
import com.example.undergraduateresearch.domain.model.Notification
import com.example.undergraduateresearch.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val api: NotificationApi
) : NotificationRepository {
    
    override suspend fun getNotifications(): Result<List<Notification>> {
        return try {
            val notifications = api.getNotifications().map { it.toDomain() }
            Result.success(notifications)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun markAsRead(notificationId: String): Result<Unit> {
        return try {
            api.markAsRead(notificationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteNotification(notificationId: String): Result<Unit> {
        return try {
            api.deleteNotification(notificationId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getUnreadCount(): Result<Int> {
        return try {
            val count = api.getUnreadCount()
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
