package com.base_clean_architecture.repository.impl

import com.base_clean_architecture.data.network.ApiService
import com.base_clean_architecture.data.response.Coffee
import com.base_clean_architecture.repository.RegistrationRepository

class RegistrationRepositoryImpl(private val api: ApiService) : RegistrationRepository {
    override suspend fun getCoffee(): Result<List<Coffee>> {
        return try {
            Result.success(api.getCoffee())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}