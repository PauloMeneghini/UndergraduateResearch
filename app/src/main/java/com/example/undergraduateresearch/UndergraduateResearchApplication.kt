package com.example.undergraduateresearch

import android.app.Application
import com.example.undergraduateresearch.di.AppContainer

/**
 * Classe Application da aplicação.
 * Inicializa o container de dependências.
 */
class UndergraduateResearchApplication : Application() {
    
    lateinit var appContainer: AppContainer
    
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
