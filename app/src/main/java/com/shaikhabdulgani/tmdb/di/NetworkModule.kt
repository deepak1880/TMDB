package com.shaikhabdulgani.tmdb.di

import com.google.firebase.auth.FirebaseAuth
import com.shaikhabdulgani.tmdb.BuildConfig
import com.shaikhabdulgani.tmdb.global.AuthInterceptor
import com.shaikhabdulgani.tmdb.global.Constants
import com.shaikhabdulgani.tmdb.home.data.remote.HomeApi
import com.shaikhabdulgani.tmdb.moviedetail.data.source.remote.MovieDetailApi
import com.shaikhabdulgani.tmdb.search.data.remote.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.API_KEY))
            .build()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit
            .create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit
            .create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDetailApi(retrofit: Retrofit): MovieDetailApi {
        return retrofit
            .create(MovieDetailApi::class.java)
    }
}