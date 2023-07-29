package io.github.xtvj.network.data

data class BaseResponse<T>(
    val code: Int,
    val `data`: T?,
    val msg: String?
)
