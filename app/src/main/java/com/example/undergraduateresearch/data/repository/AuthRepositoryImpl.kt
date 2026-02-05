package com.example.undergraduateresearch.data.repository

import android.util.Log
import com.example.undergraduateresearch.data.local.TokenManager
import com.example.undergraduateresearch.data.remote.api.AuthApiService
import com.example.undergraduateresearch.data.remote.dto.LoginRequestDto
import com.example.undergraduateresearch.domain.model.LoginResult
import com.example.undergraduateresearch.domain.model.User
import com.example.undergraduateresearch.domain.repository.AuthRepository
import com.example.undergraduateresearch.util.Resource

/**
 * Implementação do repositório de autenticação.
 * Gerencia operações de login e armazenamento de token.
 */
class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {
    
    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
    
    override suspend fun login(email: String, password: String): Resource<LoginResult> {
        Log.d(TAG, "--- AuthRepository.login() ---")
        Log.d(TAG, "Preparando requisição de login...")
        
        return try {
            val request = LoginRequestDto(email = email, password = password)
            Log.d(TAG, "Request DTO criado: email=$email")
            
            Log.d(TAG, "Fazendo chamada à API...")
            val response = authApiService.login(request)
            
            Log.d(TAG, "Resposta recebida!")
            Log.d(TAG, "Status Code: ${response.code()}")
            Log.d(TAG, "Is Successful: ${response.isSuccessful}")
            Log.d(TAG, "Has Body: ${response.body() != null}")
            
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Log.d(TAG, "✅ Resposta bem-sucedida!")
                Log.d(TAG, "Corpo da resposta (raw): $body")
                Log.d(TAG, "Token recebido: ${body.accessToken?.take(20) ?: "null"}...")
                Log.d(TAG, "Dados do profissional:")
                Log.d(TAG, "  - ID: ${body.profissional?.idUserMae ?: "null"}")
                Log.d(TAG, "  - Email: ${body.profissional?.email ?: "null"}")
                Log.d(TAG, "  - ID Binômio: ${body.profissional?.idBinomio ?: "null"}")
                Log.d(TAG, "  - Ativo: ${body.profissional?.ativo ?: "null"}")
                
                // Validar se os dados essenciais estão presentes
                if (body.accessToken == null) {
                    Log.e(TAG, "❌ Token está nulo na resposta!")
                    return Resource.Error("Token não recebido do servidor")
                }
                
                if (body.profissional == null) {
                    Log.e(TAG, "❌ Dados do profissional estão nulos na resposta!")
                    return Resource.Error("Dados do profissional não recebidos do servidor")
                }
                
                if (body.profissional.idUserMae == null || body.profissional.email == null) {
                    Log.e(TAG, "❌ Dados essenciais do profissional estão nulos!")
                    Log.e(TAG, "  - idUserMae: ${body.profissional.idUserMae}")
                    Log.e(TAG, "  - email: ${body.profissional.email}")
                    return Resource.Error("Dados incompletos do profissional")
                }
                
                // Mapear DTO para modelo de domínio
                Log.d(TAG, "Mapeando DTO para modelo de domínio...")
                val user = User(
                    idUserMae = body.profissional.idUserMae!!,
                    email = body.profissional.email!!,
                    nome = null  // API não retorna nome, apenas email
                )
                
                val loginResult = LoginResult(
                    accessToken = body.accessToken!!,
                    user = user
                )
                Log.d(TAG, "Modelo de domínio criado com sucesso")
                
                // Salvar token automaticamente
                Log.d(TAG, "Salvando token...")
                saveToken(body.accessToken!!)
                Log.d(TAG, "Token salvo com sucesso!")
                
                Log.d(TAG, "Retornando Resource.Success")
                Resource.Success(loginResult)
            } else {
                val errorMsg = "Erro ao fazer login: ${response.code()}"
                Log.e(TAG, "❌ $errorMsg")
                
                // Tentar ler corpo de erro
                try {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Error Body: $errorBody")
                } catch (e: Exception) {
                    Log.e(TAG, "Não foi possível ler error body: ${e.message}")
                }
                
                Resource.Error(errorMsg)
            }
        } catch (e: Exception) {
            val errorMsg = "Erro de conexão: ${e.message}"
            Log.e(TAG, "❌ EXCEÇÃO CAPTURADA!")
            Log.e(TAG, "Tipo: ${e::class.simpleName}")
            Log.e(TAG, "Mensagem: ${e.message}")
            Log.e(TAG, "Stack trace:", e)
            Resource.Error(errorMsg)
        }
    }
    
    override suspend fun saveToken(token: String) {
        tokenManager.salvarToken(token)
    }
    
    override suspend fun getToken(): String? {
        return tokenManager.lerToken()
    }
    
    override suspend fun clearToken() {
        tokenManager.limparDados()
    }
}
