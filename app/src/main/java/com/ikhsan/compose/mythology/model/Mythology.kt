package com.ikhsan.compose.mythology.model

data class Mythology(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val favorite: Boolean = false
)
