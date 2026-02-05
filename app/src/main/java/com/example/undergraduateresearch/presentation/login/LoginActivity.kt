package com.example.undergraduateresearch.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.undergraduateresearch.presentation.feed.FeedActivity
import com.example.undergraduateresearch.R
import com.example.undergraduateresearch.RegisterActivity
import com.example.undergraduateresearch.UndergraduateResearchApplication
import com.example.undergraduateresearch.presentation.ViewModelFactory
import com.example.undergraduateresearch.util.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "LoginActivity"
    }
    
    private val viewModel: LoginViewModel by viewModels {
        Log.d(TAG, "Criando LoginViewModel...")
        val appContainer = (application as UndergraduateResearchApplication).appContainer
        ViewModelFactory { LoginViewModel(appContainer.loginUseCase) }
    }
    
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var registerButton: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "========================================")
        Log.d(TAG, "LoginActivity.onCreate()")
        Log.d(TAG, "========================================")
        
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        Log.d(TAG, "Configurando views...")
        setupViews()
        
        Log.d(TAG, "Configurando observers...")
        setupObservers()
        
        Log.d(TAG, "Configurando listeners...")
        setupListeners()
        
        Log.d(TAG, "LoginActivity pronta!")
    }
    
    private fun setupViews() {
        emailInput = findViewById(R.id.input_email)
        passwordInput = findViewById(R.id.input_password)
        loginButton = findViewById(R.id.btn_login)
        registerButton = findViewById(R.id.btn_register)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.loginState.collect { resource ->
                Log.d(TAG, "--- Estado do login mudou ---")
                Log.d(TAG, "Novo estado: ${resource?.let { it::class.simpleName } ?: "null"}")
                
                when (resource) {
                    is Resource.Loading -> {
                        Log.d(TAG, "Estado: LOADING")
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        Log.d(TAG, "Estado: SUCCESS")
                        showLoading(false)
                        val loginResult = resource.data!!
                        
                        Log.d(TAG, "Dados recebidos:")
                        Log.d(TAG, "  - Nome: ${loginResult.user.nome}")
                        Log.d(TAG, "  - Email: ${loginResult.user.email}")
                        Log.d(TAG, "  - Token: ${loginResult.accessToken.take(20)}...")
                        
                        Toast.makeText(
                            this@LoginActivity,
                            "Bem-vinda, ${loginResult.user.nome ?: "usuária"}!",
                            Toast.LENGTH_LONG
                        ).show()
                        
                        Log.d(TAG, "Navegando para FeedActivity...")
                        navigateToFeed()
                    }
                    is Resource.Error -> {
                        Log.e(TAG, "Estado: ERROR")
                        Log.e(TAG, "Mensagem de erro: ${resource.message}")
                        showLoading(false)
                        Toast.makeText(
                            this@LoginActivity,
                            resource.message ?: "Erro ao fazer login",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    null -> {
                        Log.d(TAG, "Estado: NULL (inicial)")
                        showLoading(false)
                    }
                }
            }
        }
    }
    
    private fun setupListeners() {
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        
        loginButton.setOnClickListener {
            Log.d(TAG, "========================================")
            Log.d(TAG, "Botão de login clicado!")
            Log.d(TAG, "========================================")
            
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            
            Log.d(TAG, "Input do usuário:")
            Log.d(TAG, "  - Email: $email")
            Log.d(TAG, "  - Password: ${password.length} characters")
            
            Log.d(TAG, "Validando inputs...")
            val validationError = viewModel.validateInputs(email, password)
            if (validationError != null) {
                Log.w(TAG, "Validação falhou: $validationError")
                Toast.makeText(this, validationError, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            Log.d(TAG, "Validação OK! Iniciando login...")
            viewModel.login(email, password)
        }
    }
    
    private fun showLoading(isLoading: Boolean) {
        loginButton.isEnabled = !isLoading
        loginButton.text = if (isLoading) "Entrando..." else "Entrar"
    }
    
    private fun navigateToFeed() {
        val intent = Intent(this, FeedActivity::class.java)
        startActivity(intent)
        finish()
    }
}