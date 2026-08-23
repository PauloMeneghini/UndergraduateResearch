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

class NewsCarouselAdapter : RecyclerView.Adapter<NewsCarouselAdapter.CarouselViewHolder>() {
    private var articleList: List<Article> = emptyList()

    fun submitList(list: List<Article>) {
        articleList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story_card, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int = articleList.size

    class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageStory: ImageView = itemView.findViewById(R.id.imageStory)
        private val textStoryTitle: TextView = itemView.findViewById(R.id.textStoryTitle)
        private val iconPlay: ImageView = itemView.findViewById(R.id.iconPlay)

        fun bind(article: Article) {
            Glide.with(itemView.context)
                .load(article.imageUrl ?: R.drawable.sample_story_background)
                .placeholder(R.drawable.sample_story_background)
                .error(R.drawable.sample_story_background)
                .into(imageStory)

            textStoryTitle.text = article.title ?: "Sem Título"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDetailActivity::class.java).apply {
                    putExtra(NewsDetailActivity.EXTRA_TITLE, article.title)
                    putExtra(NewsDetailActivity.EXTRA_CATEGORY, article.category)
                    putExtra(NewsDetailActivity.EXTRA_AUTHOR, article.author)
                    putExtra(NewsDetailActivity.EXTRA_DATE, article.publishedAt)
                    putExtra(NewsDetailActivity.EXTRA_IMAGE_URL, article.imageUrl)
                    putExtra(NewsDetailActivity.EXTRA_BLOCKS, article.rawBlocks)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}
