package com.develop.base_android.injection

val gson = Provider.provideGson()

val httpLoggingInterceptor = Provider.provideLoggingInterceptor()

val authInterceptor = NetWorkModule.AuthInterceptor(gson)

val client = NetWorkModule.provideAuthClient()

val clientBuilder = NetWorkModule.provideAuthClientBuilder().build()

val apiServiceNotToken = Provider.providerRetrofitApiServiceNotToken()