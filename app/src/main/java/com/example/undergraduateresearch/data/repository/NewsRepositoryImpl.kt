package com.example.undergraduateresearch.data.repository

import android.util.Log
import com.example.undergraduateresearch.data.remote.api.NewsApiService
import com.example.undergraduateresearch.domain.model.Article
import com.example.undergraduateresearch.domain.repository.NewsRepository
import com.example.undergraduateresearch.util.Resource

/**
 * Implementação do repositório de notícias.
 * Gerencia operações de busca de notícias.
 */
class NewsRepositoryImpl(
    private val newsApiService: NewsApiService
) : NewsRepository {
    
    companion object {
        private const val TAG = "NewsRepositoryImpl"
    }
    
    override suspend fun getTopHeadlines(category: String): Resource<List<Article>> {
        Log.d(TAG, "--- NewsRepository.getTopHeadlines() ---")
        Log.d(TAG, "Categoria: $category")
        
        return try {
            Log.d(TAG, "Fazendo chamada à API de notícias...")
            val response = newsApiService.getTopHeadlines(category)
            
            Log.d(TAG, "Resposta recebida!")
            Log.d(TAG, "Status Code: ${response.code()}")
            Log.d(TAG, "Is Successful: ${response.isSuccessful}")
            Log.d(TAG, "Has Body: ${response.body() != null}")
            
            if (response.isSuccessful && response.body() != null) {
                val articlesDto = response.body()!!.articles
                Log.d(TAG, "✅ ${articlesDto.size} notícias recebidas")
                
                // Mapear DTOs para modelos de domínio e filtrar artigos inválidos
                val articles = articlesDto.mapNotNull { dto ->
                    // Filtrar artigos sem título ou URL (dados essenciais)
                    if (dto.title.isNullOrBlank() || dto.url.isNullOrBlank()) {
                        Log.w(TAG, "⚠️ Artigo ignorado - título ou URL nulo")
                        null
                    } else {
                        Article(
                            title = dto.title,
                            description = dto.description,
                            urlToImage = dto.urlToImage,
                            url = dto.url,
                            content = dto.content,
                            sourceName = dto.source?.name ?: "Fonte desconhecida",
                            author = dto.author,
                            publishedAt = dto.publishedAt
                        )
                    }
                }
                
                Log.d(TAG, "Notícias válidas após filtragem: ${articles.size}")
                Resource.Success(articles)
            } else {
                val errorMsg = "Erro ao buscar notícias: ${response.code()}"
                Log.e(TAG, "❌ $errorMsg")
                
                // Tentar ler corpo de erro
                try {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Error Body: $errorBody")
                } catch (e: Exception) {
                    Log.e(TAG, "Não foi possível ler error body: ${e.message}")
                }
                
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            val errorMsg = "Erro de conexão: ${e.message}"
            Log.e(TAG, "❌ EXCEÇÃO CAPTURADA!")
            Log.e(TAG, "Tipo: ${e::class.simpleName}")
            Log.e(TAG, "Mensagem: ${e.message}")
            Log.e(TAG, "Stack trace:", e)
            Resource.Error(errorMsg)
        }
    }
}
