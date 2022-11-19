package com.develop.base_android.data.network

import com.develop.base_android.data.response.MyResponse
import com.develop.base_android.injection.apiServiceNotToken
import kotlinx.coroutines.flow.Flow

fun APIRequest.getEntry(): Flow<MyResponse> =
    request(
        ApiRouter(
            APIPath.entry(),
            HTTPMethod.GET
        ),
        apiServiceNotToken
    )
