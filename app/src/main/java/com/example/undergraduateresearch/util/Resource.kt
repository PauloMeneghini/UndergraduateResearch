package com.example.undergraduateresearch.util

/**
 * Classe selada que representa o estado de uma operação assíncrona.
 * Utilizada para encapsular resultados de operações que podem ter sucesso, erro ou estar em carregamento.
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    /**
     * Estado de sucesso com dados.
     */
    class Success<T>(data: T) : Resource<T>(data)
    
    /**
     * Estado de erro com mensagem.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    
    /**
     * Estado de carregamento.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
