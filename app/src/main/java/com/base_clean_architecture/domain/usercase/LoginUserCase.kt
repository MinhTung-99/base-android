package com.base_clean_architecture.domain.usercase

import com.base_clean_architecture.data.response.Coffee
import com.base_clean_architecture.domain.RegistrationRepository

class LoginUserCase (private val loginRepository: RegistrationRepository) {

    suspend fun invoke (): Result<List<Coffee>> {
        return loginRepository.getCoffee()
    }
}