package com.shaikhabdulgani.tmdb.auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaikhabdulgani.tmdb.auth.presentation.AuthState
import com.shaikhabdulgani.tmdb.auth.domain.repository.AuthRepository
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import com.shaikhabdulgani.tmdb.auth.domain.validation.AuthValidators
import com.shaikhabdulgani.tmdb.auth.domain.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validator: AuthValidators
) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState>
        get() = _loginState

    private val _authState = MutableSharedFlow<AuthState>()
    val authState: SharedFlow<AuthState> get() = _authState

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.LoginClick -> login()
            is LoginEvent.OnEmailChange -> onEmailChange(event.email)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.password)
        }
    }

    private fun onPasswordChange(password: String) {
        val validationResult: ValidationResult = validator.passwordValidator.validate(password)
        if (validationResult.isValid) {
            _loginState.value = _loginState.value.copy(
                password = password,
                passwordError = ""
            )
            return
        }
        _loginState.value = _loginState.value.copy(
            password = password,
            passwordError = validationResult.error
        )
    }

    private fun onEmailChange(email: String) {
        val validationResult: ValidationResult = validator.emailValidator.validate(email)
        if (validationResult.isValid) {
            _loginState.value = _loginState.value.copy(email = email, emailError = "")
            return
        }
        _loginState.value =
            _loginState.value.copy(email = email, emailError = validationResult.error)
    }

    fun login() = viewModelScope.launch(Dispatchers.IO) {

        _authState.emit(AuthState.Loading)

        val loginState = _loginState.value

        onEmailChange(loginState.emailError)
        onPasswordChange(loginState.password)

        if (loginState.emailError.isNotBlank() || loginState.passwordError.isNotBlank()) {
            return@launch
        }

        authRepository.login(
            loginState.email,
            loginState.password
        ).collect {
            when (it) {
                is Resource.Error -> {
                    _authState.emit(AuthState.Failure(it.message!!))
                }

                is Resource.Loading -> {
                    _authState.emit(AuthState.Loading)
                }

                is Resource.Success -> {
                    _authState.emit(AuthState.Success(it.data!!.uid))
                }
            }
        }
    }
}

