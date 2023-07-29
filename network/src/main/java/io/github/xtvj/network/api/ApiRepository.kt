package io.github.xtvj.network.api

import io.github.xtvj.network.data.BaseResponse
import io.github.xtvj.network.data.BaseResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject


class ApiRepository @Inject constructor(
    private val service: ApiService
) {

    //POST请求需要转换为josn格式的请求体
    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        JSONObject(mapOf(*params)).toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

    private fun <T> getResult(result: BaseResponse<T>?): BaseResult<T> {
        return if (result?.code == 200) {
            BaseResult(true, result.msg, result.data)
        } else {
            BaseResult(false, result?.msg ?: "未知失败", null)
        }
    }


//    suspend fun login(username: String, password: String): BaseResult<String> {
//        val response = service.login(
//            createJsonRequestBody(
//                "username" to username,
//                "password" to password
//            )
//        ).getOrNull()
//        return getResult(response)
//    }
//
//    suspend fun checkProfile(): BaseResult<String> {
//        return getResult(service.checkProfile().getOrNull())
//    }

//    suspend fun upLoadAvatar(file: File): BaseResult<String> {
//        val requestFile: RequestBody = RequestBody.create(
//            "image/*".toMediaTypeOrNull(), file
//        )
//        val body: MultipartBody.Part = MultipartBody.Part.createFormData("avatarfile", file.name, requestFile)
//        val response = service.updateAvatar(body).getOrNull()
//        return if (response?.code == 200) {
//            BaseResult(true, null, response.imgUrl)
//        } else {
//            BaseResult(false, response?.msg ?: "失败")
//        }
//    }

}