package com.develop.base_android.injection

import com.develop.base_android.BuildConfig
import com.develop.base_android.data.network.ApiServiceNotToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object Provider {

    @Provides
    fun providerRetrofitApiServiceNotToken(
        @AuthInterceptorClient okHttpClient: OkHttpClient
    ): ApiServiceNotToken =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            // .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(ApiServiceNotToken::class.java)

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .generateNonExecutableJson()
            .create()
    }
}