package com.shaikhabdulgani.tmdb.di

import com.shaikhabdulgani.tmdb.auth.domain.validation.AuthValidators
import com.shaikhabdulgani.tmdb.auth.domain.validation.EmailValidator
import com.shaikhabdulgani.tmdb.auth.domain.validation.PasswordValidator
import com.shaikhabdulgani.tmdb.auth.domain.validation.RepeatPasswordValidator
import com.shaikhabdulgani.tmdb.auth.domain.validation.UsernameValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAuthValidator(): AuthValidators {
        return AuthValidators(
            emailValidator = EmailValidator(),
            passwordValidator = PasswordValidator(),
            rePasswordValidator = RepeatPasswordValidator(),
            usernameValidator = UsernameValidator(),
        )
    }
}