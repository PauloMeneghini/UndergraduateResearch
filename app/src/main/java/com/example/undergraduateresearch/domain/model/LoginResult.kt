package com.example.undergraduateresearch.domain.model

/**
 * Modelo de domínio representando o resultado de uma operação de login.
 */
data class LoginResult(
    val accessToken: String,
    val user: User
)
