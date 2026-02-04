package com.example.undergraduateresearch.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para requisição de login.
 */
data class LoginRequestDto(
    @SerializedName("email")
    val email: String,
    
    @SerializedName("senha")
    val senha: String
)

/**
 * DTO para resposta de login.
 */
data class LoginResponseDto(
    @SerializedName("access_token")
    val accessToken: String?,
    
    @SerializedName("profissional")
    val profissional: ProfissionalDto?
)

/**
 * DTO para dados do profissional (mãe).
 */
data class ProfissionalDto(
    @SerializedName("id_user_mae")
    val idUserMae: Int?,
    
    @SerializedName("id_binomio")
    val idBinomio: Int?,
    
    @SerializedName("email")
    val email: String?,
    
    @SerializedName("ativo")
    val ativo: Boolean?,
    
    @SerializedName("data_cadastro")
    val dataCadastro: String?,
    
    @SerializedName("ultimo_login")
    val ultimoLogin: String?
)
