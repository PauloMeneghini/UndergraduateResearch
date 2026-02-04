package com.example.undergraduateresearch.domain.model

/**
 * Modelo de domínio representando um artigo de notícia.
 */
data class Article(
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val url: String?,
    val content: String?,
    val sourceName: String?,
    val author: String?,
    val publishedAt: String?
)
