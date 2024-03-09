package dev.sdex.github.data

import dev.sdex.github.data.model.GithubUser
import dev.sdex.github.data.model.GithubUserDetails
import dev.sdex.github.domain.model.User
import dev.sdex.github.domain.model.UserDetails

class UserMapper {

    fun map(user: GithubUser) = User(
        id = user.id,
        login = user.login,
        avatarUrl = user.avatarUrl,
    )

    fun map(userDetails: GithubUserDetails) = UserDetails(
        id = userDetails.id,
        login = userDetails.login,
        name = userDetails.name,
        bio = userDetails.bio,
        avatarUrl = userDetails.avatarUrl,
        company = userDetails.company,
        location = userDetails.location,
        publicRepos = userDetails.publicRepos,
    )
}