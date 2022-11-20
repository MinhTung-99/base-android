package com.develop.base_android.data.network

import com.develop.base_android.data.response.MyResponse
import kotlinx.coroutines.flow.Flow

fun APIRequest.getEntry(): Flow<MyResponse> =
    request(
        ApiRouter(
            APIPath.entry(),
            HTTPMethod.GET
        ),
        API_SERVICE_NOT_TOKEN
    )
