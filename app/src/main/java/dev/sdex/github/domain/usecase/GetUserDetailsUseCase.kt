package dev.sdex.github.domain.usecase

import dev.sdex.github.domain.model.UserDetails
import dev.sdex.github.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserDetailsUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke(username: String): Flow<Result<UserDetails>> {
        return repository.getUser(username = username)
    }
}