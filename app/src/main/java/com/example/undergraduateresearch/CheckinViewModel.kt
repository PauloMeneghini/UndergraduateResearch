package com.example.undergraduateresearch

import androidx.lifecycle.ViewModel

class CheckinViewModel : ViewModel() {
    var nomeUsuario: String = ""
    var sentimentoAmamentacao: String = ""
    var nivelDor: String = ""

    fun finalizarCheckin() {
        println("Check-in finalizado: $nomeUsuario sentiu $sentimentoAmamentacao com $nivelDor")
    }
}