package dev.sdex.github.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.sdex.github.ui.details.UserDetailsScreen
import dev.sdex.github.ui.details.UserDetailsViewModel
import dev.sdex.github.ui.list.UserListScreen
import dev.sdex.github.ui.list.UserListViewModel
import dev.sdex.github.ui.navigation.Route
import dev.sdex.github.ui.theme.GithubTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubTheme {
                val navController = rememberNavController()
                KoinContext {
                    NavHost(
                        navController,
                        startDestination = Route.UsersList,
                    ) {
                        composable<Route.UsersList> {
                            val viewModel = koinViewModel<UserListViewModel>()
                            UserListScreen(
                                viewModel = viewModel,
                                navHostController = navController,
                            )
                        }
                        composable<Route.Profile> { backStackEntry ->
                            val routeProfile = backStackEntry.toRoute<Route.Profile>()
                            val username = routeProfile.username
                            val viewModel = koinViewModel<UserDetailsViewModel>()
                            UserDetailsScreen(
                                viewModel = viewModel,
                                navController = navController,
                                username = username,
                            )
                        }
                    }
                }
            }
        }
    }
}
