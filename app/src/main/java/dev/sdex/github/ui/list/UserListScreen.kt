package dev.sdex.github.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import dev.sdex.github.R
import dev.sdex.github.domain.model.User
import dev.sdex.github.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val users = viewModel.usersList.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = {
                            navHostController.navigate(Route.Profile("sdex"))
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(id = R.string.accessibility_label_info),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        if (users.loadState.refresh is LoadState.Loading) {
            LoadingIndicator()
        }
        LazyColumn(
            state = lazyListState,
            modifier = modifier.padding(innerPadding),
        ) {
            items(
                count = users.itemCount,
                key = users.itemKey { it.id },
                contentType = users.itemContentType { "user" },
            ) { index ->
                val item = users[index]
                if (item != null) {
                    UserItem(
                        user = item,
                        onClick = { navHostController.navigate(Route.Profile(item.login)) },
                    )
                }
            }
        }
        if (users.loadState.refresh is LoadState.Error && users.itemCount == 0) {
            Box(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                Text(
                    text = stringResource(id = R.string.load_users_error),
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun UserItem(
    user: User,
    onClick: (User) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .clickable {
                onClick(user)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = stringResource(
                R.string.accessibility_label_profile_picture,
                user.login,
            ),
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = user.login,
                modifier = Modifier,
            )
        }
    }
}
