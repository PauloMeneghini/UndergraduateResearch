package com.example.undergraduateresearch.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.undergraduateresearch.domain.model.Article
import com.example.undergraduateresearch.domain.usecase.GetTopHeadlinesUseCase
import com.example.undergraduateresearch.util.Constants
import com.example.undergraduateresearch.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de feed de notícias.
 * Gerencia o estado de carregamento e exibição de notícias.
 */
class FeedViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {
    
    private val _newsState = MutableStateFlow<Resource<List<Article>>?>(null)
    val newsState: StateFlow<Resource<List<Article>>?> = _newsState.asStateFlow()
    
    init {
        // Carrega notícias automaticamente ao criar o ViewModel
        loadNews()
    }
    
    /**
     * Carrega as principais notícias.
     */
    fun loadNews(category: String = Constants.NEWS_CATEGORY_DEFAULT) {
        viewModelScope.launch {
            _newsState.value = Resource.Loading()
            _newsState.value = getTopHeadlinesUseCase(category)
        }
    }
    
    /**
     * Recarrega as notícias.
     */
    fun refresh() {
        loadNews()
    }
}
