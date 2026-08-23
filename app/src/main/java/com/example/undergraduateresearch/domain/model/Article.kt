package com.example.undergraduateresearch.domain.model

/**
 * Modelo de domínio representando um artigo de notícia.
 */
data class Article(
    val id: String,
    val title: String?,
    val category: String?,
    val contentPreview: String?, // Extraído dos blocos para visualização prévia
    val imageUrl: String?, // URL da Imagem de Capa
    val rawBlocks: String?, // String JSON pura dos blocos (textos e imagens internas)
    val author: String?,
    val publishedAt: String?,
    val link: String?
)
