package com.example.undergraduateresearch.presentation.notification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.undergraduateresearch.R
import com.example.undergraduateresearch.UndergraduateResearchApplication
import com.example.undergraduateresearch.databinding.ActivityNotificationBinding
import com.example.undergraduateresearch.presentation.ViewModelFactory
import com.example.undergraduateresearch.util.Resource

class NotificationActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var viewModel: NotificationViewModel
    private lateinit var adapter: NotificationAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupViewModel()
        setupRecyclerView()
        observeNotifications()
        
        // Load notifications
        viewModel.loadNotifications()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Notificações"
        }
    }
    
    private fun setupViewModel() {
        val appContainer = (application as UndergraduateResearchApplication).appContainer
        val factory = ViewModelFactory(
            appContainer.loginUseCase,
            appContainer.getTopHeadlinesUseCase,
            appContainer.getNotificationsUseCase,
            appContainer.markNotificationAsReadUseCase
        )
        viewModel = ViewModelProvider(this, factory)[NotificationViewModel::class.java]
    }
    
    private fun setupRecyclerView() {
        adapter = NotificationAdapter { notification ->
            // Mark as read when clicked
            if (!notification.isRead) {
                viewModel.markAsRead(notification.id)
            }
            
            // Handle notification click (e.g., navigate to related content)
            Toast.makeText(this, "Notificação: ${notification.title}", Toast.LENGTH_SHORT).show()
        }
        
        binding.notificationsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = this@NotificationActivity.adapter
        }
    }
    
    private fun observeNotifications() {
        viewModel.notifications.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.notificationsRecyclerView.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    binding.emptyTextView.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    
                    if (resource.data.isNullOrEmpty()) {
                        binding.emptyTextView.visibility = View.VISIBLE
                        binding.notificationsRecyclerView.visibility = View.GONE
                    } else {
                        binding.notificationsRecyclerView.visibility = View.VISIBLE
                        binding.emptyTextView.visibility = View.GONE
                        adapter.submitList(resource.data)
                    }
                    
                    binding.errorTextView.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.notificationsRecyclerView.visibility = View.GONE
                    binding.emptyTextView.visibility = View.GONE
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.errorTextView.text = resource.message
                }
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
