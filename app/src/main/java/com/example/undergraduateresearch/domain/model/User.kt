package com.example.undergraduateresearch.domain.model

/**
 * Modelo de domínio representando um usuário autenticado.
 */
data class User(
    val idUserMae: Int,
    val email: String,
    val nome: String?
)
