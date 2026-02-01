package com.mall.hrmnew.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mall.hrmnew.data.repository.AuthRepository
import com.mall.hrmnew.model.ui.auth.LoginUiState
import com.mall.hrmnew.util.UserSharedPreference
import com.mall.hrmnew.util.getDeviceId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userSharedPreference: UserSharedPreference
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email,
            emailError = null,
            generalError = null
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password,
            passwordError = null,
            generalError = null
        )
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        val state = _uiState.value
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)

        if (emailError != null || passwordError != null) {
            _uiState.value = state.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, generalError = null)

            val result = authRepository.signIn(
                email = state.email,
                password = state.password,
                deviceId = getDeviceId()
            )

            result.fold(
                onSuccess = { response ->
                    // Save token to shared preferences
                    response.token?.let { token ->
                        userSharedPreference.setToken(token)
                    }

                    _uiState.value = state.copy(
                        isLoading = false,
                        isLoggedIn = true
                    )

                    onSuccess()
                },
                onFailure = { error ->
                    _uiState.value = state.copy(
                        isLoading = false,
                        generalError = error.message ?: "Login failed. Please try again."
                    )
                }
            )
        }
    }

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email is required"
        if (!email.contains("@")) return "Invalid email format"
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Password is required"
        if (password.length < 6) return "Password must be at least 6 characters"
        return null
    }

    private fun getDeviceType(): String {
        // Platform-specific implementation can be added using expect/actual
        // For now, default to "android"
        return "android"
    }
}
