package com.app

data class News(
    val title: String,
    val date: String,
    val author_name: String,
    val thumbnail_pic_s: String,
    val url: String
)

data class NewsResult(val stat: String, val data: List<News>)

data class NewsResponse(val reason: String, val result: NewsResult, val error_code: Int)