package com.example.undergraduateresearch.data.repository

import android.util.Log
import com.example.undergraduateresearch.data.remote.api.NewsApiService
import com.example.undergraduateresearch.domain.model.Article
import com.example.undergraduateresearch.domain.repository.NewsRepository
import com.example.undergraduateresearch.util.Resource

/**
 * Implementação do repositório de notícias.
 * Gerencia operações de busca de notícias do Feed.
 */
class NewsRepositoryImpl(
    private val newsApiService: NewsApiService
) : NewsRepository {
    
    companion object {
        private const val TAG = "NewsRepositoryImpl"
    }
    
    override suspend fun getTopHeadlines(): Resource<List<Article>> {
        Log.d(TAG, "--- NewsRepository.getTopHeadlines() ---")
        
        return try {
            Log.d(TAG, "Fazendo chamada à API de feed...")
            val response = newsApiService.getTopHeadlines()
            
            Log.d(TAG, "Resposta recebida!")
            Log.d(TAG, "Status Code: ${response.code()}")
            Log.d(TAG, "Is Successful: ${response.isSuccessful}")
            Log.d(TAG, "Has Body: ${response.body() != null}")
            
            if (response.isSuccessful && response.body() != null) {
                val articlesDto = response.body()!!
                Log.d(TAG, "✅ ${articlesDto.size} notícias recebidas")
                
                // Mapear DTOs para modelos de domínio e filtrar artigos inválidos
                val articles = articlesDto.mapNotNull { dto ->
                    if (dto.titulo.isNullOrBlank()) {
                        Log.w(TAG, "⚠️ Artigo ignorado - título nulo")
                        null
                    } else {
                        // Extrair texto limpo dos blocos JSON
                        val preview = extractTextFromBlocks(dto.blocos)
                        
                        Article(
                            id = dto.id,
                            title = dto.titulo,
                            category = dto.categoria,
                            contentPreview = preview,
                            imageUrl = formatLocalhostUrl(dto.imagemCapa),
                            rawBlocks = dto.blocos?.toString(),
                            author = dto.autorEmail,
                            publishedAt = dto.criadoEm,
                            link = dto.link
                        )
                    }
                }
                
                Log.d(TAG, "Notícias válidas após filtragem: ${articles.size}")
                Resource.Success(articles)
            } else {
                val errorMsg = "Erro ao buscar feed: ${response.code()}"
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
    
    private fun extractTextFromBlocks(blocksElement: com.google.gson.JsonElement?): String {
        if (blocksElement == null || !blocksElement.isJsonArray) return "Sem conteúdo"
        
        val stringBuilder = java.lang.StringBuilder()
        try {
            val blocksArray = blocksElement.asJsonArray
            for (block in blocksArray) {
                if (!block.isJsonObject) continue
                val blockObj = block.asJsonObject
                
                val type = blockObj.get("type")?.asString
                if (type == "paragraph" || type == "heading") {
                    val contentArray = blockObj.get("content")?.asJsonArray
                    if (contentArray != null) {
                        for (contentItem in contentArray) {
                            if (!contentItem.isJsonObject) continue
                            val contentObj = contentItem.asJsonObject
                            if (contentObj.get("type")?.asString == "text") {
                                val text = contentObj.get("text")?.asString
                                if (!text.isNullOrEmpty()) {
                                    stringBuilder.append(text)
                                }
                            }
                        }
                        stringBuilder.append("\n\n")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao extrair texto do JSON: ${e.message}")
            return blocksElement.toString().take(150) + "..."
        }
        
        val result = stringBuilder.toString().trim()
        return if (result.isEmpty()) "Conteúdo indisponível" else result
    }
    
    private fun formatLocalhostUrl(url: String?): String? {
        if (url == null) return null
        return url.replace("http://localhost", "http://10.0.2.2")
    }
}
