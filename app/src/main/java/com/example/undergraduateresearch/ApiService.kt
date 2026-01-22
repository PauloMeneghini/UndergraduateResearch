package com.example.undergraduateresearch

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/mae/login")
    suspend fun fazerLogin(@Body request: LoginRequest): Response<LoginResponse>
}