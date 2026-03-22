package com.example.undergraduateresearch.domain.repository

import com.example.undergraduateresearch.domain.model.Notification

interface NotificationRepository {
    suspend fun getNotifications(): Result<List<Notification>>
    suspend fun markAsRead(notificationId: String): Result<Unit>
    suspend fun deleteNotification(notificationId: String): Result<Unit>
    suspend fun getUnreadCount(): Result<Int>
}
