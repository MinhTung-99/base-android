package com.develop.base_android.injection

import com.develop.base_android.data.local.Settings
import com.develop.base_android.data.network.HTTPError
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object NetWorkModule {

    fun provideAuthClient(): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(authInterceptor)
    }.build()

    fun provideAuthClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder().apply {
        //if (BuildConfig.DEBUG) {
        addInterceptor(httpLoggingInterceptor)
        //addInterceptor(networkConnectionInterceptor)
        //}
    }

    class AuthInterceptor(
        private val gson: Gson
    ) : Interceptor {
        private val mutex = Mutex()

        override fun intercept(chain: Interceptor.Chain): Response {
            val req = chain.request()
            val token = runBlocking { Settings.ACCESS_TOKEN.get("") }
            val res = chain.proceedWithToken(req, token)

            if (res.code != HTTPError.UNAUTHORIZED.code) {
                return res
            }

            /*val newToken: String? = runBlocking {
                mutex.withLock {
                    when (Settings.REFRESH_TOKEN.get("")) {
                        "" -> {
                            (MainApplication.visibleActivity as? MainActivity)?.logOut()
                            null
                        }
                        else -> {
                            try {
                                val router = ApiRouter(
                                    APIPath.refreshToken(), HTTPMethod.POST,
                                    parameters = toMap(
                                        RefreshTokenRequest(
                                            Settings.REFRESH_TOKEN.get("")
                                        )
                                    ).filterNotNullValues().toHashMap()
                                )
                                val refreshTokenRes =
                                    apiService.post(router.url(), router.headers, router.parameters)
                                val result: com.example.hokenbox.data.network.Response<LoginResponse> =
                                    gson.fromJson(refreshTokenRes.string())
                                safeLet(
                                    result.data?.token_type,
                                    result.data?.access_token
                                ) { token_type, access_token ->
                                    val stringToken = "$token_type $access_token"
                                    Settings.ACCESS_TOKEN.put(stringToken)
                                    return@withLock stringToken
                                }
                                return@withLock null
                            } catch (e: Exception) {
                                (MainApplication.visibleActivity as? MainActivity)?.logOut()
                                null
                            }
                        }
                    }
                }
            }
            return if (newToken !== null) chain.proceedWithToken(req, newToken) else res*/
            return res
        }

        private fun Interceptor.Chain.proceedWithToken(req: Request, token: String?): Response =
            req.newBuilder()
                .apply {
                    if (token !== null) {
                        addHeader("Authorization", token)
                    }
                }
                .build()
                .let(::proceed)
    }
}