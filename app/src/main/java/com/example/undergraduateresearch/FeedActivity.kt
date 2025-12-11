// FeedActivity.kt

package com.example.undergraduateresearch


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var carouselAdapter: NewsCarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feed)

        setupViewPager()
        setupRecyclerView()
        fetchNewsFromApi()

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
    private fun fetchNewsFromApi() {
        Log.d("FeedActivity", "Iniciando busca de notícias da API...")

        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getTopHeadlines(category = "aleitamento")
                }

                if (response.isSuccessful && response.body() != null) {
                    val articles = response.body()!!.articles

                    Log.d("FeedActivity", "API retornou ${articles.size} artigos")

                    if (articles.isNotEmpty()) {
                        Log.d("FeedActivity", "Primeiro artigo: ${articles[0].title}")

                        val carouselArticles = articles.take(5)
                        carouselAdapter.submitList(carouselArticles)
                        Log.d("FeedActivity", "Carrossel atualizado com ${carouselArticles.size} artigos")

                        newsAdapter.submitList(articles)
                        Log.d("FeedActivity", "RecyclerView atualizado com sucesso")
                    } else {
                        Log.w("FeedActivity", "Lista de artigos está vazia")
                        Toast.makeText(this@FeedActivity, "Nenhuma notícia encontrada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMsg = "Erro ao buscar notícias: ${response.code()} - ${response.message()}"
                    Log.e("FeedActivity", errorMsg)
                    Toast.makeText(this@FeedActivity, "Erro ao carregar notícias", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                val errorMsg = "Exceção ao buscar notícias: ${e.message}"
                Log.e("FeedActivity", errorMsg, e)
                Toast.makeText(this@FeedActivity, "Erro de conexão: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
