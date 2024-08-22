package com.shaikhabdulgani.tmdb.auth.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaikhabdulgani.tmdb.auth.presentation.AuthState
import com.shaikhabdulgani.tmdb.auth.domain.repository.AuthRepository
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import com.shaikhabdulgani.tmdb.auth.domain.validation.AuthValidators
import com.shaikhabdulgani.tmdb.auth.domain.validation.ValidationResult
import com.shaikhabdulgani.tmdb.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val validator: AuthValidators
) : ViewModel() {

    private val _signUpState = mutableStateOf(SignUpState())
    val signUpState: State<SignUpState>
        get() = _signUpState

    private val _authState = MutableSharedFlow<AuthState>()
    val authState: SharedFlow<AuthState> get() = _authState

    fun onEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.SignUpClick -> signUp()
            is SignUpEvent.OnEmailChange -> onEmailChange(event.email)
            is SignUpEvent.OnPasswordChange -> onPasswordChange(event.password)
            is SignUpEvent.OnRePasswordChange -> onRePasswordChange(event.password)
            is SignUpEvent.OnUsernameChange -> onUsernameChange(event.username)
        }
    }

    private fun onRePasswordChange(password: String) {
        val validationResult: ValidationResult =
            validator.rePasswordValidator.validate(Pair(password, signUpState.value.password))
        if (validationResult.isValid) {
            _signUpState.value = _signUpState.value.copy(
                repeatPassword = password,
                repeatPasswordError = ""
            )
            return
        }
        _signUpState.value = _signUpState.value.copy(
            repeatPassword = password,
            repeatPasswordError = validationResult.error
        )
    }

    private fun onUsernameChange(username: String) {
        val validationResult: ValidationResult = validator.usernameValidator.validate(username)
        if (validationResult.isValid) {
            _signUpState.value = _signUpState.value.copy(
                username = username,
                usernameError = ""
            )
            return
        }
        _signUpState.value = _signUpState.value.copy(
            username = username,
            usernameError = validationResult.error
        )
    }

    private fun onPasswordChange(password: String) {
        val validationResult: ValidationResult = validator.passwordValidator.validate(password)
        if (validationResult.isValid) {
            _signUpState.value = _signUpState.value.copy(
                password = password,
                passwordError = ""
            )
            return
        }
        _signUpState.value = _signUpState.value.copy(
            password = password,
            passwordError = validationResult.error
        )
    }

    private fun onEmailChange(email: String) {
        val validationResult: ValidationResult = validator.emailValidator.validate(email)
        if (validationResult.isValid) {
            _signUpState.value = _signUpState.value.copy(email = email, emailError = "")
            return
        }
        _signUpState.value =
            _signUpState.value.copy(email = email, emailError = validationResult.error)
    }

    private fun signUp() = viewModelScope.launch(Dispatchers.IO) {
        val signUpState = _signUpState.value

        onEmailChange(signUpState.emailError)
        onPasswordChange(signUpState.password)
        onUsernameChange(signUpState.password)

        if (
            signUpState.emailError.isNotBlank() ||
            signUpState.passwordError.isNotBlank() ||
            signUpState.usernameError.isNotBlank()
        ) {
            return@launch
        }

        _authState.emit(AuthState.Loading)

        authRepository.signUp(
            email = signUpState.email,
            username = signUpState.username,
            password = signUpState.password
        ).collect {
            when (it) {
                is Resource.Error -> {
                    _authState.emit(AuthState.Failure(it.message!!))
                }

                is Resource.Loading -> {
                    _authState.emit(AuthState.Loading)
                }

                is Resource.Success -> {
                    userRepository.addUser(
                        email = signUpState.email,
                        username = signUpState.username
                    ).collect { resource ->
                        when (resource) {
                            is Resource.Error -> {
                                _authState.emit(AuthState.Failure(it.message!!))
                            }

                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                _authState.emit(AuthState.Success(""))
                            }
                        }
                    }
                }
            }
        }
    }
}

