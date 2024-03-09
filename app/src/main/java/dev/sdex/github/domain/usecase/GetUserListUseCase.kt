package dev.sdex.github.domain.usecase

import androidx.paging.PagingData
import dev.sdex.github.domain.model.User
import dev.sdex.github.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserListUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke(): Flow<PagingData<User>> {
        return repository.getUsers()
    }
}