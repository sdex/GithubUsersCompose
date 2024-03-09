package dev.sdex.github.domain.model

import androidx.annotation.Keep

@Keep
data class UserDetails(
    val id: Int,
    val login: String,
    val avatarUrl: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val bio: String? = null,
    val publicRepos: Int,
)
