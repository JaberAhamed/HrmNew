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
            println("LoginViewModel: Validation failed - emailError: $emailError, passwordError: $passwordError")
            _uiState.value = state.copy(
                emailError = emailError,
                passwordError = passwordError
            )
            return
        }

        println("LoginViewModel: Attempting login with email: ${state.email}, deviceId: ${getDeviceId()}")
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, generalError = null)

            val result = authRepository.signIn(
                email = state.email,
                password = state.password,
                deviceId = getDeviceId()
            )

            result.fold(
                onSuccess = { response ->
                    println("LoginViewModel: Login successful - success: ${response.success}, message: ${response.message}")
                    println("LoginViewModel: User data - ${response.data?.user?.name}, token: ${response.data?.token?.take(20)}...")
                    // Save token to shared preferences
                    if (response.success){
                        response.data?.token?.let { token ->
                            userSharedPreference.setToken(token)
                            println("LoginViewModel: Token saved to shared preferences")
                        }

                        _uiState.value = state.copy(
                            isLoading = false,
                            isLoggedIn = true
                        )

                        onSuccess()
                    }else{
                        println("LoginViewModel: Login failed - ${response.message}")
                        _uiState.value = state.copy(
                            isLoading = false,
                            generalError = response.message ?: "Login failed. Please try again."
                        )

                    }

                },
                onFailure = { error ->
                    println("LoginViewModel: Network error - ${error.message}")
                    error.printStackTrace()
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
