package com.develop.base_android.data.network

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.annotation.Keep
import com.develop.base_android.BuildConfig
import com.develop.base_android.application.base.BaseViewModel
import com.develop.base_android.application.base.hideProgress
import com.develop.base_android.injection.NoConnectivityException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Keep
object APIPath {
    fun entry(): String = "entries"
}

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

@Keep
@Singleton
class APIRequest @Inject constructor(
    private val apiServiceNotToken: ApiServiceNotToken,
    val gson: Gson
) {
    companion object {
        const val BASE_URL = BuildConfig.API_ENDPOINT
    }

    inline fun <reified T> request(
        router: ApiRouter,
        apiService: String
    ): Flow<T> = flow {
        emit(gson.fromJson(getMethodCall(router, apiService).string()))
    }

    suspend inline fun <reified T> suspendRequest(
        router: ApiRouter,
        apiService: String
    ): T = gson.fromJson(getMethodCall(router, apiService).string())

    suspend fun getMethodCall(router: ApiRouter, apiService: String): ResponseBody {
        return when (router.method) {

            HTTPMethod.GET -> getService(apiService)
                .get(router.url(), router.headers, router.parameters)

            HTTPMethod.POST -> getService(apiService)
                .post(router.url(), router.headers, router.parameters)

            HTTPMethod.PUT -> getService(apiService)
                .put(router.url(), router.headers, router.parameters)

            HTTPMethod.DELETE -> getService(apiService)
                .delete(router.url(), router.headers, router.parameters)

            HTTPMethod.IMAGE -> getService(apiService)
                .uploadImage(router.url(), headers = hashMapOf(), router.image)
        }
    }

    private fun getService(apiService: String): BaseApiService = apiServiceNotToken

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

val JsonFormatter = hashMapOf(
    "accept" to "application/json",
    "Content-Type" to "application/json",
)

@Keep
fun ApiRouter.url(): String = APIRequest.BASE_URL + path

@Keep
enum class HTTPMethod {
    GET, POST, PUT, DELETE, IMAGE
}

fun Throwable.httpCode(): Int? = (this as? HttpException)?.code()

fun <T> Flow<T>.catchRetry(
    viewModel: BaseViewModel,
    customError: suspend FlowCollector<T>.(Throwable) -> Unit = {},
    cancel: suspend FlowCollector<T>.() -> Unit = {},
    retry: suspend FlowCollector<T>.(Throwable) -> Unit,
): Flow<T> {
    return catch { error ->
        viewModel.hideProgress()
        when {
            error is SocketTimeoutException || error is NoConnectivityException -> {
                Log.d("KMHUHSS", "NO INTERNET")
            }

            error.httpCode() == HTTPError.UNAUTHORIZED.code -> {

            }
        }

        Log.d("KMFG", error.toString())
    }
}