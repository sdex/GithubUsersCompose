package dev.sdex.github.domain.repository

import androidx.paging.PagingData
import dev.sdex.github.domain.model.User
import dev.sdex.github.domain.model.UserDetails
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<PagingData<User>>

    fun getUser(username: String): Flow<Result<UserDetails>>
}