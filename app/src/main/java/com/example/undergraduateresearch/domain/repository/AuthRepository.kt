package com.example.undergraduateresearch.domain.repository

import com.example.undergraduateresearch.domain.model.LoginResult
import com.example.undergraduateresearch.util.Resource

/**
 * Interface do repositório de autenticação.
 * Define o contrato para operações relacionadas à autenticação de usuários.
 */
interface AuthRepository {
    
    /**
     * Realiza login do usuário.
     * @param email Email do usuário
     * @param password User password
     * @return Resource contendo LoginResult em caso de sucesso
     */
    suspend fun login(email: String, password: String): Resource<LoginResult>
    
    /**
     * Salva o token JWT localmente.
     * @param token Token JWT a ser salvo
     */
    suspend fun saveToken(token: String)
    
    /**
     * Recupera o token JWT salvo.
     * @return Token JWT ou null se não existir
     */
    suspend fun getToken(): String?
    
    /**
     * Remove o token JWT salvo.
     */
    suspend fun clearToken()
}
