package dev.sdex.github.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.sdex.github.data.UserMapper
import dev.sdex.github.data.source.remote.GithubService
import dev.sdex.github.domain.model.User
import retrofit2.HttpException
import java.io.IOException

class UsersPagingSource(
    private val service: GithubService,
    private val userMapper: UserMapper,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val key = params.key ?: 0
        return try {
            val users = service.getUsers(key, params.loadSize)
                .map { userMapper.map(it) }
            val prevKey = null
            val nextKey = users.lastOrNull()?.id?.plus(1)
            LoadResult.Page(
                data = users,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
