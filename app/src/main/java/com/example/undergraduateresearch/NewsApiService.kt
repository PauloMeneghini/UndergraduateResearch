// NewsApiService.kt
package com.example.undergraduateresearch

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    suspend fun getTopHeadlines(
        @Query("q") category: String = "aleitamento",
        @Query("apiKey") apiKey: String = "2dc6c43129fc40fe9f5f95f431bdbaef"
    ): Response<NewsApiResponse>
}
