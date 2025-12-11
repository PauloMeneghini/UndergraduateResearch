package com.example.undergraduateresearch

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val registerButton: TextView = findViewById(R.id.btn_register)
        val loginButton: MaterialButton = findViewById(R.id.btn_login)
        val email: TextInputEditText = findViewById(R.id.input_email)
        val password: TextInputEditText = findViewById(R.id.input_password)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if(validateInputs(emailText, passwordText, email, password)) {
                performLogin(emailText, passwordText)
            }
        }
    }

    private fun validateInputs(
        emailText: String,
        passwordText: String,
        emailField: TextInputEditText,
        passwordField: TextInputEditText
    ): Boolean {
        if (emailText.isEmpty()) {
            emailField.error = "Email obrigatório"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            emailField.error = "Email inválido"
            return false
        }

        if (passwordText.isEmpty()) {
            passwordField.error = "Senha obrigatória"
            return false
        }

        if (passwordText.length < 6) {
            passwordField.error = "Senha deve ter no mínimo 6 caracteres"
            return false
        }

        return true
    }

    private fun performLogin(email: String, password: String) {
        if (email == "test@test.com" && password == "123456") {
            Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
        }
    }
}