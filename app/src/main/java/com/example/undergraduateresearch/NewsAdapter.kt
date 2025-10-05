package com.example.undergraduateresearch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var newsList: List<NewsItem> = emptyList()

    fun submitList(list: List<NewsItem>) {
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

        fun bind(news: NewsItem) {
            imageView.setImageResource(news.imageRes)
            textCategory.text = news.category
            textTitle.text = news.title

            // Adicionar clique no item
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDetailActivity::class.java).apply {
                    putExtra(NewsDetailActivity.EXTRA_TITLE, news.title)
                    putExtra(NewsDetailActivity.EXTRA_CATEGORY, news.category)
                    putExtra(NewsDetailActivity.EXTRA_IMAGE, news.imageRes)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}
