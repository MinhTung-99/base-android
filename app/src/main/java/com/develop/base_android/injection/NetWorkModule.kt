package com.develop.base_android.injection

import com.develop.base_android.application.MainApplication
import com.develop.base_android.data.local.Settings
import com.develop.base_android.data.network.HTTPError
import com.develop.base_android.data.response.utils.isInternetAvailable
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import javax.inject.Inject
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoAuthInterceptorClient

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @AuthInterceptorClient
    @Provides
    fun provideAuthClient(
        authInterceptor: AuthInterceptor,
        clientBuilder: OkHttpClient.Builder
    ): OkHttpClient = clientBuilder.apply {
        addInterceptor(authInterceptor)
    }.build()

    @Provides
    fun provideAuthClientBuilder(
        loggingInterceptor: HttpLoggingInterceptor,
        networkConnectionInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient.Builder = OkHttpClient.Builder().apply {
        //if (BuildConfig.DEBUG) {
        addInterceptor(loggingInterceptor)
        addInterceptor(networkConnectionInterceptor)
        //}
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}

class AuthInterceptor @Inject constructor(
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

class NetworkConnectionInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!MainApplication.CONTEXT.isInternetAvailable()) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}