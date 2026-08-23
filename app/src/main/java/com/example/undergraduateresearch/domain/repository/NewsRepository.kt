package com.example.undergraduateresearch.domain.repository

import com.example.undergraduateresearch.domain.model.Article
import com.example.undergraduateresearch.util.Resource

/**
 * Interface do repositório de notícias.
 * Define o contrato para operações relacionadas a notícias.
 */
interface NewsRepository {
    
    /**
     * Busca as principais notícias (Feed).
     * @return Resource contendo lista de artigos em caso de sucesso
     */
    suspend fun getTopHeadlines(): Resource<List<Article>>
}
