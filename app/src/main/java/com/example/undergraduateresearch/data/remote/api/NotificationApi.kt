package com.example.undergraduateresearch.data.remote.api

import com.example.undergraduateresearch.data.remote.dto.NotificationDto
import retrofit2.http.*

interface NotificationApi {
    @GET("notifications")
    suspend fun getNotifications(): List<NotificationDto>
    
    @PUT("notifications/{id}/read")
    suspend fun markAsRead(@Path("id") notificationId: String)
    
    @DELETE("notifications/{id}")
    suspend fun deleteNotification(@Path("id") notificationId: String)
    
    @GET("notifications/unread-count")
    suspend fun getUnreadCount(): Int
}
