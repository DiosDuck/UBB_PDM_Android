package com.example.ubb_pdm_android

data class Item(
    val id: String,
    val text: String
) {
    override fun toString(): String = text
}
