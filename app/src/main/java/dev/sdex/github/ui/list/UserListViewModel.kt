package dev.sdex.github.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.sdex.github.domain.model.User
import dev.sdex.github.domain.usecase.GetUserListUseCase
import kotlinx.coroutines.flow.Flow

class UserListViewModel(
    getUserListUseCase: GetUserListUseCase,
) : ViewModel() {

    val usersList: Flow<PagingData<User>> = getUserListUseCase()
        .cachedIn(viewModelScope)
}