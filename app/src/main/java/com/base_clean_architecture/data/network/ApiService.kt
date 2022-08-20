package com.base_clean_architecture.data.network

import com.base_clean_architecture.data.response.Coffee
import retrofit2.http.GET

interface ApiService {
    @GET("coffee/hot")
    suspend fun getCoffee(): MutableList<Coffee>
}