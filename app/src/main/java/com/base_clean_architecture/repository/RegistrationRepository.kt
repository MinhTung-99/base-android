package com.base_clean_architecture.repository

import com.base_clean_architecture.data.response.Coffee

interface RegistrationRepository {
    suspend fun getCoffee(): Result<List<Coffee>>
}