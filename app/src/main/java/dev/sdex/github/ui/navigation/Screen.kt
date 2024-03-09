package dev.sdex.github.ui.navigation

const val ARG_USERNAME = "username"

sealed class Screen(val route: String) {
    data object UsersList : Screen("userslist")
    data object Profile : Screen("profile")
}
