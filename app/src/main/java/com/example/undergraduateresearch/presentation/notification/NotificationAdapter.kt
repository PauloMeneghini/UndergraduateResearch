package com.example.undergraduateresearch.presentation.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.undergraduateresearch.R
import com.example.undergraduateresearch.domain.model.Notification
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(
    private val onNotificationClick: (Notification) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    
    private var notifications = listOf<Notification>()
    
    fun submitList(newNotifications: List<Notification>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }
    
    override fun getItemCount(): Int = notifications.size
    
    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.notificationTitle)
        private val messageTextView: TextView = itemView.findViewById(R.id.notificationMessage)
        private val timestampTextView: TextView = itemView.findViewById(R.id.notificationTimestamp)
        private val unreadIndicator: View = itemView.findViewById(R.id.unreadIndicator)
        
        fun bind(notification: Notification) {
            titleTextView.text = notification.title
            messageTextView.text = notification.message
            timestampTextView.text = formatTimestamp(notification.timestamp)
            
            // Show/hide unread indicator
            unreadIndicator.visibility = if (notification.isRead) View.GONE else View.VISIBLE
            
            // Change background for unread notifications
            itemView.alpha = if (notification.isRead) 0.7f else 1.0f
            
            itemView.setOnClickListener {
                onNotificationClick(notification)
            }
        }
        
        private fun formatTimestamp(timestamp: Long): String {
            val now = System.currentTimeMillis()
            val diff = now - timestamp
            
            return when {
                diff < 60000 -> "Agora"
                diff < 3600000 -> "${diff / 60000}m atrás"
                diff < 86400000 -> "${diff / 3600000}h atrás"
                diff < 604800000 -> "${diff / 86400000}d atrás"
                else -> {
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    sdf.format(Date(timestamp))
                }
            }
        }
    }
}
