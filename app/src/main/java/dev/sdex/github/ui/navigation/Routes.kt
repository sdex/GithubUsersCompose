package dev.sdex.github.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object UsersList : Route()

    @Serializable
    data class Profile(val username: String) : Route()
}
