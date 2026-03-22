package com.example.undergraduateresearch.domain.usecase

import com.example.undergraduateresearch.domain.model.Notification
import com.example.undergraduateresearch.domain.repository.NotificationRepository

class GetNotificationsUseCase(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(): Result<List<Notification>> {
        return repository.getNotifications()
    }
}
