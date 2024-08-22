package com.shaikhabdulgani.tmdb.di

import com.google.firebase.auth.FirebaseAuth
import com.shaikhabdulgani.tmdb.auth.data.repository.FirebaseAuthRepository
import com.shaikhabdulgani.tmdb.auth.domain.repository.AuthRepository
import com.shaikhabdulgani.tmdb.core.data.local.AppDatabase
import com.shaikhabdulgani.tmdb.core.data.repository.UserRepositoryImpl
import com.shaikhabdulgani.tmdb.core.domain.repository.UserRepository
import com.shaikhabdulgani.tmdb.home.data.remote.HomeApi
import com.shaikhabdulgani.tmdb.home.data.repository.HomeRepositoryImpl
import com.shaikhabdulgani.tmdb.home.domain.repository.HomeRepository
import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.MovieDetailDao
import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.MovieDetailApi
import com.shaikhabdulgani.tmdb.moviedetail.data.repository.MovieDetailRepositoryImpl
import com.shaikhabdulgani.tmdb.moviedetail.domain.repository.MovieDetailRepository
import com.shaikhabdulgani.tmdb.search.data.remote.SearchApi
import com.shaikhabdulgani.tmdb.search.data.repository.SearchRepositoryImpl
import com.shaikhabdulgani.tmdb.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepo(auth: FirebaseAuth): AuthRepository {
        return FirebaseAuthRepository(auth)
    }

    @Provides
    @Singleton
    fun provideHomeRepo(api: HomeApi, appDatabase: AppDatabase): HomeRepository {
        return HomeRepositoryImpl(api, appDatabase.movieDao, appDatabase.seriesDto)
    }

    @Provides
    @Singleton
    fun provideUserRepo(): UserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    @Singleton
    fun bindSearchRepo(api: SearchApi): SearchRepository {
        return SearchRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepo(api: MovieDetailApi, dao: MovieDetailDao): MovieDetailRepository {
        return MovieDetailRepositoryImpl(api, dao)
    }

}