package com.example.undergraduateresearch.data.remote.api

import com.example.undergraduateresearch.data.remote.dto.NewsApiResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface Retrofit para endpoints de notícias.
 */
interface NewsApiService {
    
    @GET("everything")
    suspend fun getTopHeadlines(
        @Query("q") query: String
    ): Response<NewsApiResponseDto>
}
