package com.example.nomorebadwords

import java.io.Serializable

data class Achievement(
    val name: String,
    val description: String,
    val daysRequired: Int,
    var isUnlocked: Boolean = false,
    val isSecret: Boolean = false
) : Serializable
