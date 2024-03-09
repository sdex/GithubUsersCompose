package dev.sdex.github.ui.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sdex.github.domain.model.UserDetails
import dev.sdex.github.domain.usecase.GetUserDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Immutable
data class UserDetailsUiState(
    val user: UserDetails? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)

class UserDetailsViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(UserDetailsUiState())
    val uiState: StateFlow<UserDetailsUiState> = _uiState.asStateFlow()

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            getUserDetailsUseCase(username = username)
                .collect { result ->
                    if (result.isSuccess) {
                        _uiState.update {
                            it.copy(
                                user = result.getOrThrow(),
                                isLoading = false,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isError = true,
                                isLoading = false,
                            )
                        }
                    }
                }
        }
    }
}