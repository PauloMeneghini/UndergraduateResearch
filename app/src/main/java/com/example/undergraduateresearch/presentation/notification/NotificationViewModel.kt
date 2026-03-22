package com.example.undergraduateresearch.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.undergraduateresearch.domain.model.Notification
import com.example.undergraduateresearch.domain.usecase.GetNotificationsUseCase
import com.example.undergraduateresearch.domain.usecase.MarkNotificationAsReadUseCase
import com.example.undergraduateresearch.util.Resource
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markAsReadUseCase: MarkNotificationAsReadUseCase
) : ViewModel() {
    
    private val _notifications = MutableLiveData<Resource<List<Notification>>>()
    val notifications: LiveData<Resource<List<Notification>>> = _notifications
    
    private val _unreadCount = MutableLiveData<Int>(0)
    val unreadCount: LiveData<Int> = _unreadCount
    
    fun loadNotifications() {
        viewModelScope.launch {
            _notifications.value = Resource.Loading()
            
            getNotificationsUseCase().fold(
                onSuccess = { notificationList ->
                    _notifications.value = Resource.Success(notificationList)
                    updateUnreadCount(notificationList)
                },
                onFailure = { error ->
                    _notifications.value = Resource.Error(
                        error.message ?: "Erro ao carregar notificações"
                    )
                }
            )
        }
    }
    
    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            markAsReadUseCase(notificationId).fold(
                onSuccess = {
                    // Reload notifications to update the list
                    loadNotifications()
                },
                onFailure = { error ->
                    // Could show error toast here
                }
            )
        }
    }
    
    private fun updateUnreadCount(notifications: List<Notification>) {
        _unreadCount.value = notifications.count { !it.isRead }
    }
}
