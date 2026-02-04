package com.example.undergraduateresearch.domain.usecase

import android.util.Log
import com.example.undergraduateresearch.domain.model.LoginResult
import com.example.undergraduateresearch.domain.repository.AuthRepository
import com.example.undergraduateresearch.util.Resource

/**
 * Use case para realizar login de usuário.
 * Encapsula a lógica de negócio de autenticação.
 */
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    companion object {
        private const val TAG = "LoginUseCase"
    }
    
    /**
     * Executa o login.
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return Resource contendo LoginResult em caso de sucesso
     */
    suspend operator fun invoke(email: String, password: String): Resource<LoginResult> {
        Log.d(TAG, "LoginUseCase invocado")
        Log.d(TAG, "Chamando AuthRepository.login()...")
        
        val result = authRepository.login(email, password)
        
        Log.d(TAG, "AuthRepository retornou: ${result::class.simpleName}")
        return result
    }
}
