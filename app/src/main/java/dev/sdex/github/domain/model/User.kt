package dev.sdex.github.domain.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val login: String,
    val avatarUrl: String,
)
