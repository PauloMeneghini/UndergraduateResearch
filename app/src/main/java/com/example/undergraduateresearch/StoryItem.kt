package com.example.undergraduateresearch

/**
 * Data class representing a story item for the story carousel.
 * Note: This is a legacy model. Consider refactoring to use Article from domain layer.
 */
data class StoryItem(
    val title: String,
    val imageRes: Int
)
