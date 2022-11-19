package com.develop.base_android.data.network

import kotlinx.coroutines.flow.Flow
import retrofit2.Response

fun APIRequest.login(loginRequest: LoginRequest): Flow<Response<LoginResponse>> =
    request(
        ApiRouter(
            APIPath.login(),
            HTTPMethod.POST,
            parameters = toMap(loginRequest).filterNotNullValues().toHashMap()
        ), false
    )