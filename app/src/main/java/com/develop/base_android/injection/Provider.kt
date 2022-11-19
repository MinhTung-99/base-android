package com.develop.base_android.injection

import com.develop.base_android.BuildConfig
import com.develop.base_android.data.network.ApiServiceNotToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Provider {

    fun providerRetrofitApiServiceNotToken(): ApiServiceNotToken =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            // .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .client(clientBuilder)
            .build().create(ApiServiceNotToken::class.java)

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .generateNonExecutableJson()
            .create()
    }
}