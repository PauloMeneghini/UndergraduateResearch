package com.example.undergraduateresearch

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoryAdapter(
    private val storyList: List<StoryItem>
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story_card, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(storyList[position])
    }

    override fun getItemCount(): Int = storyList.size

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageStory: ImageView = itemView.findViewById(R.id.imageStory)
        private val textStoryTitle: TextView = itemView.findViewById(R.id.textStoryTitle)
        private val iconPlay: ImageView = itemView.findViewById(R.id.iconPlay)

        fun bind(story: StoryItem) {
            imageStory.setImageResource(story.imageRes)
            textStoryTitle.text = story.title

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDetailActivity::class.java).apply {
                    putExtra(NewsDetailActivity.EXTRA_TITLE, story.title)
                    putExtra(NewsDetailActivity.EXTRA_CATEGORY, "TECHNOLOGY")
                    putExtra(NewsDetailActivity.EXTRA_IMAGE_URL, story.imageRes)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}