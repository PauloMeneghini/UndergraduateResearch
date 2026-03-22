package com.example.undergraduateresearch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.undergraduateresearch.domain.usecase.GetNotificationsUseCase
import com.example.undergraduateresearch.domain.usecase.GetTopHeadlinesUseCase
import com.example.undergraduateresearch.domain.usecase.LoginUseCase
import com.example.undergraduateresearch.domain.usecase.MarkNotificationAsReadUseCase
import com.example.undergraduateresearch.presentation.feed.FeedViewModel
import com.example.undergraduateresearch.presentation.login.LoginViewModel
import com.example.undergraduateresearch.presentation.notification.NotificationViewModel

/**
 * Factory para criar ViewModels com suas dependências.
 * Permite injeção manual de dependências nos ViewModels.
 */
class ViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(loginUseCase) as T
            }
            modelClass.isAssignableFrom(FeedViewModel::class.java) -> {
                FeedViewModel(getTopHeadlinesUseCase) as T
            }
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> {
                NotificationViewModel(getNotificationsUseCase, markNotificationAsReadUseCase) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
