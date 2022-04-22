package com.base_clean_architecture.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        fun provideRetrofit(/*okHttpClient: OkHttpClient*/): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://api.sampleapis.com")
                //.client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}