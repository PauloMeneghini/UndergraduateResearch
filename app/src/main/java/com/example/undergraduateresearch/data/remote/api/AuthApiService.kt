package com.example.undergraduateresearch.data.remote.api

import com.example.undergraduateresearch.data.remote.dto.LoginRequestDto
import com.example.undergraduateresearch.data.remote.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface Retrofit para endpoints de autenticação.
 */
interface AuthApiService {
    
    @POST("auth/mae/login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>
}
