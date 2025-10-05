package com.example.undergraduateresearch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Runnable
import kotlin.math.abs
import androidx.recyclerview.widget.LinearLayoutManager

class FeedActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: ImageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
        setupTransformer()
        setupRecyclerView()
        loadNews()

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable, 2000)
    }

    private fun init() {
        viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.sample_story_background)
        imageList.add(R.drawable.mask)

        adapter = ImageAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setupTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)

    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter()
        recyclerView.adapter = newsAdapter
    }

    private fun loadNews() {
        val newsList = listOf(
            NewsItem(
                id = 1,
                category = "TECHNOLOGY",
                title = "Insurtech startup PasarPolis gets \$54 million — Series B",
                imageRes = R.drawable.breastfeeding_amico
            ),
            NewsItem(
                id = 2,
                category = "TECHNOLOGY",
                title = "The IPO parade continues as Wish files, Bumble targets",
                imageRes = R.drawable.breastfeeding_amico
            ),
            NewsItem(
                id = 3,
                category = "TECHNOLOGY",
                title = "Hypatos gets \$11.8M for a deep learning approach",
                imageRes = R.drawable.breastfeeding_amico
            ),
            NewsItem(
                id = 4,
                category = "TECHNOLOGY",
                title = "Insurtech startup PasarPolis gets \$54 million — Series B",
                imageRes = R.drawable.breastfeeding_amico
            ),

        )
        newsAdapter.submitList(newsList)
    }
}