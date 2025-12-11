// NewsResponse.kt
package com.example.undergraduateresearch

import com.google.gson.annotations.SerializedName

data class NewsApiResponse(
    @SerializedName("articles")
    val articles: List<Article>
)

data class Article(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("url")
    val url: String,

    @SerializedName("content")
    val content: String?,

    @SerializedName("source")
    val source: Source,

    @SerializedName("author")
    val author: String,
    
    @SerializedName("publishedAt")
    val publishedAt: String?
)

data class Source(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String
)