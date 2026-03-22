package com.example.undergraduateresearch.domain.model

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val type: NotificationType,
    val data: Map<String, String>? = null
)

enum class NotificationType {
    NEWS_UPDATE,
    SYSTEM_ALERT,
    REMINDER,
    GENERAL
}
