package dev.sdex.github.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.sdex.github.data.UserMapper
import dev.sdex.github.data.source.local.UsersDatabase
import dev.sdex.github.data.source.remote.GithubService
import dev.sdex.github.domain.model.User
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator(
    private val service: GithubService,
    private val database: UsersDatabase,
    private val userMapper: UserMapper,
) : RemoteMediator<Int, User>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        val userDao = database.userDao()
        val pageSize = if (loadType == LoadType.REFRESH) {
            state.config.initialLoadSize
        } else {
            state.config.pageSize
        }
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    val lastItem = database.userDao().getLastUser()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true,
                        )
                    lastItem.id
                }
            }

            val users = service.getUsers(
                since = loadKey ?: 0,
                perPage = pageSize,
            ).map { userMapper.map(it) }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDao.clearAll()
                }
                userDao.insertAll(users)
            }

            MediatorResult.Success(
                endOfPaginationReached = users.size < pageSize,
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
