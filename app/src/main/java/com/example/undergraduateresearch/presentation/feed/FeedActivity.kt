package com.example.undergraduateresearch.presentation.feed

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.undergraduateresearch.R
import com.example.undergraduateresearch.UndergraduateResearchApplication
import com.example.undergraduateresearch.presentation.ViewModelFactory
import com.example.undergraduateresearch.presentation.feed.adapter.NewsAdapter
import com.example.undergraduateresearch.presentation.feed.adapter.NewsCarouselAdapter
import com.example.undergraduateresearch.presentation.notification.NotificationActivity
import com.example.undergraduateresearch.util.NotificationHelper
import com.example.undergraduateresearch.util.Resource
import kotlinx.coroutines.launch

class FeedActivity : AppCompatActivity() {
    
    private val viewModel: FeedViewModel by viewModels {
        val appContainer = (application as UndergraduateResearchApplication).appContainer
        ViewModelFactory(
            appContainer.loginUseCase,
            appContainer.getTopHeadlinesUseCase,
            appContainer.getNotificationsUseCase,
            appContainer.markNotificationAsReadUseCase
        )
    }
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var carouselAdapter: NewsCarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        // Request notification permission for Android 13+
        NotificationHelper.requestNotificationPermission(this)

        // 🧪 TESTE: Mostrar notificação de boas-vindas
        // TODO: Remover este código após testar
        NotificationHelper.showNotification(
            context = this,
            notificationId = 100,
            title = "Bem-vinda ao Feed! 🎉",
            message = "Sistema de notificações funcionando perfeitamente!"
        )

        setupNotificationButton()
        setupViewPager()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupNotificationButton() {
        val notificationButton = findViewById<ImageButton>(R.id.notificationButton)
        notificationButton?.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewPager() {
        viewPager2 = findViewById(R.id.viewPager2)
        carouselAdapter = NewsCarouselAdapter()
        viewPager2.adapter = carouselAdapter

        viewPager2.offscreenPageLimit = 1

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        viewPager2.setPageTransformer { page, position ->
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager2.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                page.translationX = offset
            } else {
                page.translationY = offset
            }

            page.apply {
                val scaleFactor = 0.85f
                val alphaFactor = 0.3f
                when {
                    position < -1 || position > 1 -> {
                        alpha = 0f
                        scaleY = scaleFactor
                        translationZ = -1f
                    }
                    position <= 0 -> {
                        // Card da esquerda
                        alpha = 1 + position * (1 - alphaFactor)
                        scaleY = scaleFactor + (1 - scaleFactor) * (1 + position)
                        translationZ = -Math.abs(position)
                    }
                    position <= 1 -> {
                        // Card da direita
                        alpha = 1 - position * (1 - alphaFactor)
                        scaleY = scaleFactor + (1 - scaleFactor) * (1 - position)
                        translationZ = -Math.abs(position)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter()
        recyclerView.adapter = newsAdapter
    }
    
    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.newsState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Pode adicionar um indicador de loading aqui
                    }
                    is Resource.Success -> {
                        val articles = resource.data!!
                        
                        // Atualizar carousel com os primeiros 5 artigos
                        val carouselArticles = articles.take(5)
                        carouselAdapter.submitList(carouselArticles)
                        
                        // Atualizar RecyclerView com todos os artigos
                        newsAdapter.submitList(articles)
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            this@FeedActivity,
                            resource.message ?: "Erro ao carregar notícias",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    null -> {
                        // Estado inicial
                    }
                }
            }
        }
    }
}

