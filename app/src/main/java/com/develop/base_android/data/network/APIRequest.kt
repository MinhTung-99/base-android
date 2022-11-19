package com.develop.base_android.data.network

import androidx.annotation.Keep
import com.develop.base_android.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.HttpException

@Keep
enum class HTTPError(val code: Int) {
    UNAUTHORIZED(401),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    PERMISSION_DENIED(406),
    MAINTENANCE(407),
    VERSION_UPGRADE(408),
    SERVER_ERROR(500)
}

class APIRequest(
    private val service: ApiService,
    private val serviceNoLogin: ApiServiceNoLogin,
    val gson: Gson
) {
    companion object {
        const val BASE_URL = BuildConfig.API_ENDPOINT
    }

    inline fun <reified T> request(
        router: ApiRouter,
        needLogin: Boolean = true
    ): Flow<T> = flow {
        emit(gson.fromJson(getMethodCall(router, needLogin).string()))
    }

    suspend inline fun <reified T> suspendRequest(
        router: ApiRouter,
        needLogin: Boolean = true
    ): T = gson.fromJson(getMethodCall(router, needLogin).string())

    suspend fun getMethodCall(router: ApiRouter, needLogin: Boolean): ResponseBody =
        when (router.method) {
            HTTPMethod.GET -> getService(needLogin)
                .get(router.url(), router.headers, router.parameters)
            HTTPMethod.POST -> getService(needLogin)
                .post(router.url(), router.headers, router.parameters)
            HTTPMethod.PUT -> getService(needLogin)
                .put(router.url(), router.headers, router.parameters)
            HTTPMethod.DELETE -> getService(needLogin)
                .delete(router.url(), router.headers, router.parameters)
            HTTPMethod.IMAGE -> getService(needLogin)
                .uploadImage(router.url(), headers = hashMapOf(), router.image)
        }

    private fun getService(needLogin: Boolean): BaseApiService {
        return if (needLogin) service else serviceNoLogin
    }
}

@Keep
inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

@Keep
data class ApiRouter(
    val path: String,
    val method: HTTPMethod = HTTPMethod.GET,
    val parameters: Parameters = hashMapOf(),
    val headers: Headers = JsonFormatter,
    val image: MultipartBody.Part? = null,
)

val BaseHeader = hashMapOf(
    "language" to "jp",
    "platform" to "android",
    "app-version" to BuildConfig.VERSION_NAME,
)

val JsonFormatter = hashMapOf(
    "accept" to "application/json",
    "Content-Type" to "application/json",
).apply {
    this.putAll(BaseHeader)
}

@Keep
fun ApiRouter.url(): String = APIRequest.BASE_URL + path

@Keep
enum class HTTPMethod {
    GET, POST, PUT, DELETE, IMAGE
}

fun Throwable.httpCode(): Int? = (this as? HttpException)?.code()