package com.example.undergraduateresearch.util

/**
 * Constantes utilizadas em toda a aplicação.
 */
object Constants {
    
    // URLs de API
    const val BASE_URL_AUTH = "http://192.168.0.208:3000/"
    const val BASE_URL_NEWS = "https://newsapi.org/v2/"
    
    // API Keys
    const val NEWS_API_KEY = "2dc6c43129fc40fe9f5f95f431bdbaef"  // Substitua pela sua chave da NewsAPI
    
    // SharedPreferences
    const val PREFS_NAME = "secure_prefs"
    const val KEY_JWT_TOKEN = "KEY_JWT_TOKEN"
    
    // Categorias de Notícias
    const val NEWS_CATEGORY_DEFAULT = "aleitamento"
    
    // Validação
    const val MIN_PASSWORD_LENGTH = 6
}
