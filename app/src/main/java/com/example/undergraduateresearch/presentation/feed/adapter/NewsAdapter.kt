package com.example.undergraduateresearch.presentation.feed.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.undergraduateresearch.NewsDetailActivity
import com.example.undergraduateresearch.R
import com.example.undergraduateresearch.domain.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var newsList: List<Article> = emptyList()

    fun submitList(list: List<Article>) {
        newsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageNews)
        private val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)

        fun bind(article: Article) {
            Glide.with(itemView.context)
                .load(R.drawable.sample_story_background) // Imagem provisória
                .into(imageView)

            textCategory.text = article.category ?: "Sem Categoria"
            textTitle.text = article.title ?: "Sem Título"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDetailActivity::class.java).apply {
                    putExtra(NewsDetailActivity.EXTRA_TITLE, article.title)
                    putExtra(NewsDetailActivity.EXTRA_CATEGORY, article.category)
                    putExtra(NewsDetailActivity.EXTRA_AUTHOR, article.author)
                    putExtra(NewsDetailActivity.EXTRA_DATE, article.publishedAt)
                    putExtra(NewsDetailActivity.EXTRA_CONTENT, article.contentPreview)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}