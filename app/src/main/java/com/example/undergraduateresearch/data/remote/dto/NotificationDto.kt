package com.example.undergraduateresearch.data.remote.dto

import com.example.undergraduateresearch.domain.model.Notification
import com.example.undergraduateresearch.domain.model.NotificationType

data class NotificationDto(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean,
    val type: String,
    val data: Map<String, String>?
)

fun NotificationDto.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        message = message,
        timestamp = timestamp,
        isRead = isRead,
        type = try {
            NotificationType.valueOf(type)
        } catch (e: IllegalArgumentException) {
            NotificationType.GENERAL
        },
        data = data
    )
}
