package io.github.xtvj.network.data

/**
 * @param success 网络请求是否成功
 * @param msg 网络请求返回的错误信息,success为true时为成功信息
 * @param result 网络请求成功时返回的数据
 */
data class BaseResult<T>(
    val success: Boolean = false,
    val msg: String? = null,
    val result: T? = null
)