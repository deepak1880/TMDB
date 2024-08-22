package com.shaikhabdulgani.tmdb.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shaikhabdulgani.tmdb.R
import com.shaikhabdulgani.tmdb.auth.presentation.AuthState
import com.shaikhabdulgani.tmdb.auth.domain.model.LoginResult
import com.shaikhabdulgani.tmdb.auth.domain.repository.AuthRepository
import com.shaikhabdulgani.tmdb.core.domain.util.Resource
import com.shaikhabdulgani.tmdb.auth.domain.validation.AuthValidators
import com.shaikhabdulgani.tmdb.auth.domain.validation.EmailValidator
import com.shaikhabdulgani.tmdb.auth.domain.validation.PasswordValidator
import com.shaikhabdulgani.tmdb.auth.domain.validation.UsernameValidator
import com.shaikhabdulgani.tmdb.core.presentation.DialogContent
import com.shaikhabdulgani.tmdb.core.presentation.GradientButton
import com.shaikhabdulgani.tmdb.core.presentation.InputText
import com.shaikhabdulgani.tmdb.auth.domain.validation.RepeatPasswordValidator
import com.shaikhabdulgani.tmdb.ui.theme.DarkBg
import com.shaikhabdulgani.tmdb.ui.theme.GradientEnd
import com.shaikhabdulgani.tmdb.ui.theme.GradientStart
import com.shaikhabdulgani.tmdb.ui.theme.TMDBTheme
import com.shaikhabdulgani.tmdb.ui.theme.White
import com.shaikhabdulgani.tmdb.ui.theme.White50
import com.shaikhabdulgani.tmdb.ui.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow


@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(context) {
        viewModel.authState.collectLatest {
            when (it) {
                is AuthState.Failure -> {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                    showDialog = false
                }

                AuthState.Loading -> {
                    showDialog = true
                }

                is AuthState.Success -> {
                    onLoginSuccess()
                    showDialog = false
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp)
                .blur(MaterialTheme.spacing.defaultSmall),
            painter = painterResource(id = R.drawable.bg_onboarding),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default),
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = MaterialTheme.spacing.default,
                        topEnd = MaterialTheme.spacing.default
                    )
                )
                .background(color = DarkBg)
                .padding(MaterialTheme.spacing.default)
        ) {
            val (email, emailError, password, passwordError) = viewModel.loginState.value
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.defaultSmall),
                text = stringResource(id = R.string.login),
                fontSize = 35.sp,
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        listOf(GradientStart, GradientEnd)
                    )
                )
            )
            Text(
                modifier = Modifier.offset(y = -MaterialTheme.spacing.default),
                text = stringResource(R.string.login_welcome_message),
                color = White50
            )

            InputText(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.email_placeholder),
                icon = Icons.Outlined.Email,
                value = email,
                keyboardType = KeyboardType.Email,
                errorMessage = emailError,
                onTextChange = { viewModel.onEvent(LoginEvent.OnEmailChange(it)) }
            )
            InputText(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.password),
                placeholder = stringResource(R.string.password_placeholder),
                icon = Icons.Outlined.Lock,
                value = password,
                keyboardType = KeyboardType.Password,
                errorMessage = passwordError,
                onTextChange = { viewModel.onEvent(LoginEvent.OnPasswordChange(it)) },
                isPassword = true,
            )

            Text(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.forget_password),
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                    .offset(y = -MaterialTheme.spacing.defaultSmall),
                text = stringResource(R.string.forget_password),
                color = White,
            )

            GradientButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onEvent(LoginEvent.LoginClick)
                }
            ) {
                Text(text = stringResource(R.string.login))
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            ) {
                Text(
                    text = stringResource(R.string.do_not_have_an_account),
                    color = White,
                )
                Text(
                    modifier = Modifier.clickable {
                        onSignUpClick()
                    },
                    text = stringResource(R.string.sign_up),
                    color = GradientStart,
                )
            }
        }
        if (showDialog) {
            DialogContent(message = stringResource(id = R.string.please_wait))
        }
    }

}

@Preview
@Composable
private fun LoginPreview() {
    TMDBTheme {
        LoginScreen(
            onLoginSuccess = {},
            onSignUpClick = {},
            viewModel = LoginViewModel(
                object : AuthRepository {
                    override suspend fun login(
                        email: String,
                        password: String
                    ): Flow<Resource<LoginResult>> {
                        return flow { }
                    }

                    override suspend fun signUp(
                        email: String,
                        username: String,
                        password: String
                    ): Flow<Resource<LoginResult>> {
                        return flow { }
                    }
                },
                AuthValidators(
                    emailValidator = EmailValidator(),
                    passwordValidator = PasswordValidator(),
                    rePasswordValidator = RepeatPasswordValidator(),
                    usernameValidator = UsernameValidator(),
                )
            ),
        )
    }
}