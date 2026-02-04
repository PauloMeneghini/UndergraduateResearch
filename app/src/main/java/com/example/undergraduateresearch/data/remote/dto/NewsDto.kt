package com.example.undergraduateresearch.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para resposta da API de notícias.
 */
data class NewsApiResponseDto(
    @SerializedName("articles")
    val articles: List<ArticleDto>
)

/**
 * DTO para artigo de notícia.
 */
data class ArticleDto(
    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("source")
    val source: SourceDto?,

    @SerializedName("author")
    val author: String?,
    
    @SerializedName("publishedAt")
    val publishedAt: String?
)

/**
 * DTO para fonte da notícia.
 */
data class SourceDto(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?
)
