package com.shaikhabdulgani.tmdb.global

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shaikhabdulgani.tmdb.core.presentation.util.clearBackStack
import com.shaikhabdulgani.tmdb.home.presentation.HomeScreen
import com.shaikhabdulgani.tmdb.auth.presentation.login.LoginScreen
import com.shaikhabdulgani.tmdb.auth.presentation.login.LoginViewModel
import com.shaikhabdulgani.tmdb.moviedetail.presentation.MovieDetailScreen
import com.shaikhabdulgani.tmdb.onboarding.OnboardingScreen
import com.shaikhabdulgani.tmdb.auth.presentation.signup.SignUpScreen
import com.shaikhabdulgani.tmdb.auth.presentation.signup.SignUpViewModel
import com.shaikhabdulgani.tmdb.home.presentation.HomeViewModel
import com.shaikhabdulgani.tmdb.moviedetail.presentation.MovieDetailViewModel
import com.shaikhabdulgani.tmdb.search.presentation.SearchScreen
import com.shaikhabdulgani.tmdb.search.presentation.SearchViewModel
import com.shaikhabdulgani.tmdb.ui.theme.TMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = Screen.Home) {
        composable<Screen.Onboarding> {
            OnboardingScreen(controller)
        }
        composable<Screen.Login> {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                onLoginSuccess = {
                    controller.navigate(Screen.Home) {
                        clearBackStack(controller)
                    }
                },
                onSignUpClick = { controller.navigate(Screen.SignUp) },
                viewModel = viewModel
            )
        }
        composable<Screen.SignUp> {
            val viewModel: SignUpViewModel = hiltViewModel()
            SignUpScreen(
                controller = controller,
                viewModel = viewModel
            )
        }
        composable<Screen.Home> {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(controller, viewModel)
        }

        composable<Screen.Search> {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(controller,viewModel)
        }
        composable<Screen.MovieDetail> {
            val args = it.toRoute<Screen.MovieDetail>()
            val viewModel = hiltViewModel<MovieDetailViewModel>()
            MovieDetailScreen(
                id = args.id,
                mediaType = args.mediaType,
                controller = controller,
                viewModel = viewModel
            )
        }
    }
}