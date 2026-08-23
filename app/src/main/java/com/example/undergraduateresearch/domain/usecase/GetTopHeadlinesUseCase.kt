package com.example.undergraduateresearch.domain.usecase

import com.example.undergraduateresearch.domain.model.Article
import com.example.undergraduateresearch.domain.repository.NewsRepository
import com.example.undergraduateresearch.util.Resource

/**
 * Use case para buscar as principais notícias.
 * Encapsula a lógica de negócio de busca de notícias.
 */
class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    /**
     * Executa a busca de notícias.
     * @return Resource contendo lista de artigos em caso de sucesso
     */
    suspend operator fun invoke(): Resource<List<Article>> {
        return newsRepository.getTopHeadlines()
    }
}
