package com.myapplication.model

data class User(
    val name: String = "",
    val desc: String = "",
    val avatarUrl: String = ""
) {
    companion object {
        val EMPTY = User()
    }
}