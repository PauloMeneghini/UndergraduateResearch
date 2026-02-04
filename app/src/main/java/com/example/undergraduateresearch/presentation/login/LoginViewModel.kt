package com.example.undergraduateresearch.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.undergraduateresearch.domain.model.LoginResult
import com.example.undergraduateresearch.domain.usecase.LoginUseCase
import com.example.undergraduateresearch.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de login.
 * Gerencia o estado de autenticação e validação de inputs.
 */
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    
    companion object {
        private const val TAG = "LoginViewModel"
    }
    
    private val _loginState = MutableStateFlow<Resource<LoginResult>?>(null)
    val loginState: StateFlow<Resource<LoginResult>?> = _loginState.asStateFlow()
    
    /**
     * Realiza o login do usuário.
     */
    fun login(email: String, password: String) {
        Log.d(TAG, "=== INICIANDO LOGIN ===")
        Log.d(TAG, "Email: $email")
        Log.d(TAG, "Senha: ${if (password.isNotEmpty()) "***${password.length} caracteres***" else "vazia"}")
        
        viewModelScope.launch {
            Log.d(TAG, "Definindo estado como Loading...")
            _loginState.value = Resource.Loading()
            
            Log.d(TAG, "Executando LoginUseCase...")
            val result = loginUseCase(email, password)
            
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "✅ LOGIN SUCESSO!")
                    Log.d(TAG, "Token recebido: ${result.data?.accessToken?.take(20)}...")
                    Log.d(TAG, "Usuário: ${result.data?.user?.nome} (${result.data?.user?.email})")
                    Log.d(TAG, "ID: ${result.data?.user?.idUserMae}")
                }
                is Resource.Error -> {
                    Log.e(TAG, "❌ LOGIN FALHOU!")
                    Log.e(TAG, "Erro: ${result.message}")
                }
                is Resource.Loading -> {
                    Log.d(TAG, "Estado Loading mantido")
                }
            }
            
            _loginState.value = result
            Log.d(TAG, "=== FIM DO PROCESSO DE LOGIN ===")
        }
    }
    
    /**
     * Reseta o estado de login.
     */
    fun resetLoginState() {
        _loginState.value = null
    }
    
    /**
     * Valida os inputs de login.
     * @return Mensagem de erro ou null se válido
     */
    fun validateInputs(email: String, password: String): String? {
        Log.d(TAG, "--- Validando inputs ---")
        Log.d(TAG, "Email fornecido: $email")
        Log.d(TAG, "Senha fornecida: ${password.length} caracteres")
        
        if (email.isEmpty()) {
            Log.w(TAG, "⚠️ Validação falhou: Email vazio")
            return "Email obrigatório"
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.w(TAG, "⚠️ Validação falhou: Email inválido")
            return "Email inválido"
        }
        
        if (password.isEmpty()) {
            Log.w(TAG, "⚠️ Validação falhou: Senha vazia")
            return "Senha obrigatória"
        }
        
        if (password.length < 6) {
            Log.w(TAG, "⚠️ Validação falhou: Senha muito curta (${password.length} caracteres)")
            return "Senha deve ter no mínimo 6 caracteres"
        }
        
        Log.d(TAG, "✅ Validação passou!")
        return null
    }
}
