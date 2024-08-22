package com.shaikhabdulgani.tmdb.di

import android.content.Context
import androidx.room.Room
import com.shaikhabdulgani.tmdb.core.data.local.AppDatabase
import com.shaikhabdulgani.tmdb.home.data.local.dao.MovieDao
import com.shaikhabdulgani.tmdb.moviedetail.data.source.local.MovieDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao
    }

    @Provides
    @Singleton
    fun provideSeriesDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao
    }

    @Provides
    @Singleton
    fun provideMovieDetailDao(appDatabase: AppDatabase): MovieDetailDao {
        return appDatabase.movieDetailDao
    }

}