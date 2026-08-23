package com.example.undergraduateresearch.data.remote.dto

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

/**
 * DTO para o item do Feed de Notícias.
 */
data class FeedItemDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("imagemCapa")
    val imagemCapa: String?,

    @SerializedName("titulo")
    val titulo: String?,

    @SerializedName("categoria")
    val categoria: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("blocos")
    val blocos: JsonElement?, // Pode ser lido como um JSON genérico

    @SerializedName("semanaApresentacao")
    val semanaApresentacao: Int?,

    @SerializedName("fase")
    val fase: String?,

    @SerializedName("nivelRisco")
    val nivelRisco: String?,

    @SerializedName("link")
    val link: String?,

    @SerializedName("referenciaMaterial")
    val referenciaMaterial: String?,

    @SerializedName("tipo")
    val tipo: String?,

    @SerializedName("condicaoEnvio")
    val condicaoEnvio: String?,

    @SerializedName("autorId")
    val autorId: String?,

    @SerializedName("autorEmail")
    val autorEmail: String?,

    @SerializedName("criadoEm")
    val criadoEm: String?,

    @SerializedName("atualizadoEm")
    val atualizadoEm: String?
)
