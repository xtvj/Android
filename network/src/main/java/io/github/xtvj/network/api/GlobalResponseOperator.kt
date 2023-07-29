package io.github.xtvj.network.api

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.operators.ApiResponseSuspendOperator
import io.github.xtvj.network.data.BaseResponse
import io.github.xtvj.network.interfaces.LogoutListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GlobalResponseOperator<T> constructor(
    private val logoutListener: LogoutListener
) : ApiResponseSuspendOperator<T>() {

    // The body is empty, because we will handle the success case manually.
    override suspend fun onSuccess(apiResponse: ApiResponse.Success<T>) {

        withContext(Dispatchers.Main) {
            apiResponse.run {
                if (this.data is BaseResponse<*>) {
                    if ((this.data as BaseResponse<*>).code == 401) {
                        // "App 401 退出登录:::${(this.data as BaseResponse<*>).msg}"
                        logoutListener.logout("App 401 退出登录:::${(this.data as BaseResponse<*>).msg}")
                    }
                }
            }
        }
    }

    // handles error cases when the API request gets an error response.
    // e.g., internal server error.
    override suspend fun onError(apiResponse: ApiResponse.Failure.Error<T>) {
    }

    // handles exceptional cases when the API request gets an exception response.
    // e.g., network connection error, timeout.
    override suspend fun onException(apiResponse: ApiResponse.Failure.Exception<T>) {
    }

}