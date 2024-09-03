package com.shaikhabdulgani.tmdb.di

import com.shaikhabdulgani.tmdb.auth.data.repository.FirebaseAuthRepository
import com.shaikhabdulgani.tmdb.auth.domain.repository.AuthRepository
import com.shaikhabdulgani.tmdb.core.data.repository.UserRepositoryImpl
import com.shaikhabdulgani.tmdb.core.domain.repository.UserRepository
import com.shaikhabdulgani.tmdb.home.data.repository.HomeRepositoryImpl
import com.shaikhabdulgani.tmdb.home.domain.repository.HomeRepository
import com.shaikhabdulgani.tmdb.moviedetail.data.repository.MovieDetailRepositoryImpl
import com.shaikhabdulgani.tmdb.moviedetail.domain.repository.MovieDetailRepository
import com.shaikhabdulgani.tmdb.search.data.repository.SearchRepositoryImpl
import com.shaikhabdulgani.tmdb.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepo(repo: FirebaseAuthRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun provideHomeRepo(repo: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun provideUserRepo(repo: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepo(repo: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun provideMovieDetailRepo(repo: MovieDetailRepositoryImpl): MovieDetailRepository

}