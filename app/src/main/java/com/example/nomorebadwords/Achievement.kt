package com.example.nomorebadwords

data class Achievement(
    val name: String,
    val description: String,
    val daysRequired: Int,
    var isUnlocked: Boolean = false
)
