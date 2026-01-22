package com.example.undergraduateresearch

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val senha: String
)

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("profissional")
    val dadosUsuario: DadosUsuario
)

data class DadosUsuario(
    @SerializedName("id_user_mae")
    val idUserMae: Int,

    @SerializedName("id_binomio")
    val idBinomio: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("ativo")
    val ativo: Boolean,

    @SerializedName("data_cadastro")
    val dataCadastro: String,

    @SerializedName("ultimo_login")
    val ultimoLogin: String
)
