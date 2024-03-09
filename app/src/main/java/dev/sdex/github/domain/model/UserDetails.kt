package dev.sdex.github.domain.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "user_details")
data class UserDetails(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val bio: String? = null,
    val publicRepos: Int,
)
