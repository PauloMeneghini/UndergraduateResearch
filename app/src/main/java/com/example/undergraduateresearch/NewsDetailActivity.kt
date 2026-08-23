package com.example.undergraduateresearch

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.gson.JsonParser

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_IMAGE_URL = "extra_image_url"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_BLOCKS = "extra_blocks"
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
        val rawBlocks = intent.getStringExtra(EXTRA_BLOCKS)

        val formattedDate = formatDate(publishedAt)

        // Configurar views fixas
        findViewById<ImageView>(R.id.imageBack).setOnClickListener {
            finish()
        }

        // Carregar imagem de capa usando Glide
        Glide.with(this)
            .load(imageUrl ?: R.drawable.sample_story_background)
            .into(findViewById(R.id.imageNews))
            
        findViewById<TextView>(R.id.textCategory).text = category ?: "Sem Categoria"
        findViewById<TextView>(R.id.textTitle).text = title ?: "Sem Título"
        findViewById<TextView>(R.id.textAuthor).text = author ?: "Autor desconhecido"
        findViewById<TextView>(R.id.textDate).text = formattedDate

        // Processar os blocos para o container dinâmico
        val contentContainer = findViewById<LinearLayout>(R.id.contentContainer)
        renderBlocks(rawBlocks, contentContainer)
    }
    
    private fun renderBlocks(rawBlocks: String?, container: LinearLayout) {
        if (rawBlocks.isNullOrBlank()) {
            addTextView(container, "Nenhum conteúdo disponível.")
            return
        }

        try {
            val jsonElement = JsonParser.parseString(rawBlocks)
            if (!jsonElement.isJsonArray) {
                addTextView(container, "Formato de conteúdo inválido.")
                return
            }
            
            val blocksArray = jsonElement.asJsonArray
            for (block in blocksArray) {
                if (!block.isJsonObject) continue
                val blockObj = block.asJsonObject
                val type = blockObj.get("type")?.asString
                
                when (type) {
                    "paragraph", "heading" -> {
                        val contentArray = blockObj.get("content")?.asJsonArray
                        val stringBuilder = StringBuilder()
                        
                        contentArray?.forEach { contentItem ->
                            if (contentItem.isJsonObject) {
                                val contentObj = contentItem.asJsonObject
                                if (contentObj.get("type")?.asString == "text") {
                                    val text = contentObj.get("text")?.asString
                                    if (!text.isNullOrEmpty()) {
                                        stringBuilder.append(text)
                                    }
                                }
                            }
                        }
                        
                        val finalString = stringBuilder.toString()
                        if (finalString.isNotBlank()) {
                            addTextView(container, finalString, isHeading = (type == "heading"))
                        }
                    }
                    "image" -> {
                        val props = blockObj.get("props")?.asJsonObject
                        val url = props?.get("url")?.asString
                        if (!url.isNullOrBlank()) {
                            addImageView(container, url)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("NewsDetailActivity", "Erro ao processar blocos: ${e.message}")
            addTextView(container, "Erro ao carregar conteúdo do artigo.")
        }
    }

    private fun addTextView(container: LinearLayout, text: String, isHeading: Boolean = false) {
        val textView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 24
            }
            this.text = text
            setTextColor(android.graphics.Color.parseColor("#333333"))
            if (isHeading) {
                textSize = 20f
                setTypeface(null, android.graphics.Typeface.BOLD)
            } else {
                textSize = 16f
                setLineSpacing(12f, 1.2f)
            }
        }
        container.addView(textView)
    }
    
    private fun addImageView(container: LinearLayout, url: String) {
        val finalUrl = url.replace("http://localhost", "http://10.0.2.2")
        
        val imageView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                800 // Altura em pixels (pode ser ajustada)
            ).apply {
                bottomMargin = 32
                topMargin = 16
            }
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        
        Glide.with(this)
            .load(finalUrl)
            .into(imageView)
            
        container.addView(imageView)
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
}