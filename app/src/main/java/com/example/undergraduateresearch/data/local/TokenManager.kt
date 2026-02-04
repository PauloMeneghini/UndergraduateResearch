package com.example.undergraduateresearch.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun salvarToken(token: String) {
        sharedPreferences.edit().putString("KEY_JWT_TOKEN", token).apply()
    }

    fun lerToken(): String? {
        return sharedPreferences.getString("KEY_JWT_TOKEN", null)
    }

    fun limparDados() {
        sharedPreferences.edit().clear().apply()
    }
}