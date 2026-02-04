package com.example.undergraduateresearch.domain.repository

import com.example.undergraduateresearch.domain.model.Article
import com.example.undergraduateresearch.util.Resource

/**
 * Interface do repositório de notícias.
 * Define o contrato para operações relacionadas a notícias.
 */
interface NewsRepository {
    
    /**
     * Busca as principais notícias de uma categoria.
     * @param category Categoria das notícias
     * @return Resource contendo lista de artigos em caso de sucesso
     */
    suspend fun getTopHeadlines(category: String): Resource<List<Article>>
}
