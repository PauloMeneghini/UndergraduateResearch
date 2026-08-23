package com.example.undergraduateresearch.domain.model

/**
 * Modelo de domínio representando um artigo de notícia.
 */
data class Article(
    val id: String,
    val title: String?,
    val category: String?,
    val contentPreview: String?, // Extraído dos blocos para visualização prévia
    val author: String?,
    val publishedAt: String?,
    val link: String?
)
