package com.example.undergraduateresearch.di

import android.content.Context
import com.example.undergraduateresearch.data.local.TokenManager
import com.example.undergraduateresearch.data.remote.api.AuthApiService
import com.example.undergraduateresearch.data.remote.api.NewsApiService
import com.example.undergraduateresearch.data.repository.AuthRepositoryImpl
import com.example.undergraduateresearch.data.repository.NewsRepositoryImpl
import com.example.undergraduateresearch.domain.repository.AuthRepository
import com.example.undergraduateresearch.domain.repository.NewsRepository
import com.example.undergraduateresearch.domain.usecase.GetTopHeadlinesUseCase
import com.example.undergraduateresearch.domain.usecase.LoginUseCase
import com.example.undergraduateresearch.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Container de dependências da aplicação.
 * Gerencia a criação e fornecimento de todas as dependências de forma manual.
 */
class AppContainer(private val context: Context) {
    
    // Token Manager
    val tokenManager: TokenManager by lazy {
        TokenManager(context)
    }
    
    // News API Interceptor para adicionar API Key
    private val newsApiInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        
        // Adicionar apiKey como query parameter
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("apiKey", Constants.NEWS_API_KEY)
            .build()
        
        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()
        
        chain.proceed(requestWithApiKey)
    }
    
    // Logging Interceptor para debug
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    // OkHttp Client para News API (com API Key)
    private val newsOkHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(newsApiInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    
    // Retrofit instances
    private val authRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_AUTH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    private val newsRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_NEWS)
            .client(newsOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    // API Services
    private val authApiService: AuthApiService by lazy {
        authRetrofit.create(AuthApiService::class.java)
    }
    
    private val newsApiService: NewsApiService by lazy {
        newsRetrofit.create(NewsApiService::class.java)
    }
    
    // Repositories
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApiService, tokenManager)
    }
    
    val newsRepository: NewsRepository by lazy {
        NewsRepositoryImpl(newsApiService)
    }
    
    // Use Cases
    val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(authRepository)
    }
    
    val getTopHeadlinesUseCase: GetTopHeadlinesUseCase by lazy {
        GetTopHeadlinesUseCase(newsRepository)
    }
}
