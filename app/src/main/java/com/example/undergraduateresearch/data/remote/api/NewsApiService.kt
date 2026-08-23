package com.example.undergraduateresearch.data.remote.api

import com.example.undergraduateresearch.data.remote.dto.FeedItemDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface Retrofit para endpoints de notícias.
 */
interface NewsApiService {
    
    @GET("conteudos/app/feed")
    suspend fun getTopHeadlines(): Response<List<FeedItemDto>>
}
