package me.intuit.cat.data.utils.generic

class ResponseDto<T : Any?> {
    val results: T? = null

    val page: Int? = null

    val totalPages: Int? = null

    val totalResults: Int? = null
}