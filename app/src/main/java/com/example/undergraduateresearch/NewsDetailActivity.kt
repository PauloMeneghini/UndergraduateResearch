package com.example.undergraduateresearch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_IMAGE_URL = "extra_image_url"
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

        val title = intent.getStringExtra(EXTRA_TITLE)
        val category = intent.getStringExtra(EXTRA_CATEGORY)
        val imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        val author = intent.getStringExtra(EXTRA_AUTHOR)
        val publishedAt = intent.getStringExtra(EXTRA_DATE)
        val content = intent.getStringExtra(EXTRA_CONTENT) ?: getDefaultContent()

        val formattedDate = formatDate(publishedAt)

        // Configurar views
        findViewById<ImageView>(R.id.imageBack).setOnClickListener {
            finish()
        }

        // Carregar imagem usando Glide
        Glide.with(this)
            .load(imageUrl)
            .into(findViewById(R.id.imageNews))
            
        findViewById<TextView>(R.id.textCategory).text = category
        findViewById<TextView>(R.id.textTitle).text = title
        findViewById<TextView>(R.id.textAuthor).text = author
        findViewById<TextView>(R.id.textDate).text = formattedDate
        findViewById<TextView>(R.id.textContent).text = content
    }
    
    private fun formatDate(dateString: String?): String {
        if (dateString.isNullOrBlank()) {
            return "Data não disponível"
        }
        
        return try {
            val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.getDefault())
            inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
            
            val date = inputFormat.parse(dateString)

            val outputFormat = java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy — HH:mm", java.util.Locale("pt", "BR"))
            outputFormat.format(date ?: return "Data não disponível")
        } catch (e: Exception) {
            "Data não disponível"
        }
    }

    private fun getDefaultContent(): String {
        return "In the last couple of years, we've seen new teams in tech companies emerge that focus on responsible innovation, digital wellbeing, and ethical tech use. Whatever we call them, these individuals are given the task of \"leading\" ethics at their companies.\n\n" +
                "This shift represents a growing recognition that technology companies need to think more carefully about the societal impact of their products and services. The challenge is ensuring these ethics officers have real power and influence within their organizations."
    }
}