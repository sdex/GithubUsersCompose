package dev.sdex.github.data.source.remote

import dev.sdex.github.data.model.GithubUser
import dev.sdex.github.data.model.GithubUserDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int,
    ): List<GithubUser>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String,
    ): GithubUserDetails

    /*
    https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#list-users
    https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user
     */
    companion object {
        const val BASE_URL: String = "https://api.github.com/"
    }
}
