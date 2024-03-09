package dev.sdex.github.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.sdex.github.data.UserMapper
import dev.sdex.github.data.source.GithubRemoteMediator
import dev.sdex.github.data.source.local.UsersDatabase
import dev.sdex.github.data.source.remote.GithubService
import dev.sdex.github.domain.model.User
import dev.sdex.github.domain.model.UserDetails
import dev.sdex.github.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val service: GithubService,
    private val database: UsersDatabase,
    private val userMapper: UserMapper,
) : UserRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            initialLoadSize = 50,
            pageSize = 40,
            prefetchDistance = 10,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            database.userDao().getAll()
        },
        remoteMediator = GithubRemoteMediator(service, database, userMapper),
    ).flow

    override fun getUser(username: String): Flow<Result<UserDetails>> = flow {
        try {
            val userDetails = service.getUser(username = username)
            emit(Result.success(userMapper.map(userDetails)))
        } catch (e: IOException) {
            emit(Result.failure(e))
        } catch (e: HttpException) {
            emit(Result.failure(e))
        }
    }
}
