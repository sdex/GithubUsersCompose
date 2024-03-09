package dev.sdex.github.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.sdex.github.R
import dev.sdex.github.domain.model.UserDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel,
    navController: NavHostController,
    username: String,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.getUserDetails(username)
    }
    val user = uiState.user
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = username) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.accessibility_label_navigate_back),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        if (user != null) {
            UserDetails(
                user = user,
                modifier = modifier.padding(innerPadding),
            )
        } else if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        } else if (uiState.isError) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(R.string.load_profile_error),
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun UserDetails(
    user: UserDetails,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            model = user.avatarUrl,
            contentDescription = stringResource(
                R.string.accessibility_label_profile_picture,
                user.login,
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row {
                Text(
                    text = stringResource(R.string.name),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = user.name ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.company),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = user.company ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.location),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = user.location ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.public_repos),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = user.publicRepos.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.bio),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = user.bio ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
