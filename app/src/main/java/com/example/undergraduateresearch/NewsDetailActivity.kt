package com.example.undergraduateresearch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.TextView

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_CONTENT = "extra_content"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Receber dados da notícia
        val title = intent.getStringExtra(EXTRA_TITLE)
        val category = intent.getStringExtra(EXTRA_CATEGORY)
        val imageRes = intent.getIntExtra(EXTRA_IMAGE, 0)
        val author = intent.getStringExtra(EXTRA_AUTHOR) ?: "Samuel Newton"
        val date = intent.getStringExtra(EXTRA_DATE) ?: "17 June 2023 — 4:49 PM"
        val content = intent.getStringExtra(EXTRA_CONTENT) ?: getDefaultContent()

        // Configurar views
        findViewById<ImageView>(R.id.imageBack).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.imageNews).setImageResource(imageRes)
        findViewById<TextView>(R.id.textCategory).text = category
        findViewById<TextView>(R.id.textTitle).text = title
        findViewById<TextView>(R.id.textAuthor).text = author
        findViewById<TextView>(R.id.textDate).text = date
        findViewById<TextView>(R.id.textContent).text = content
    }

    private fun getDefaultContent(): String {
        return "In the last couple of years, we've seen new teams in tech companies emerge that focus on responsible innovation, digital wellbeing, and ethical tech use. Whatever we call them, these individuals are given the task of \"leading\" ethics at their companies.\n\n" +
                "This shift represents a growing recognition that technology companies need to think more carefully about the societal impact of their products and services. The challenge is ensuring these ethics officers have real power and influence within their organizations."
    }
}