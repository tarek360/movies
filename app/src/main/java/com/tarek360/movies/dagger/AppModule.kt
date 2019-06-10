package com.tarek360.movies.dagger

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.tarek360.movies.data.MoviesRepository
import com.tarek360.movies.data.MoviesRepositoryImpl
import com.tarek360.movies.network.FlickrApi
import com.tarek360.movies.rx.SchedulerProvider
import com.tarek360.movies.rx.SchedulerProviderImpl
import com.tarek360.movies.viewmodel.MovieViewModelProviders
import com.tarek360.movies.viewmodel.MovieViewModelProvidersImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideMoviesRepository(context: Context): MoviesRepository = MoviesRepositoryImpl(context)

    @Provides
    fun provideMovieViewModelProviders(factory: ViewModelProvider.Factory): MovieViewModelProviders {
        return MovieViewModelProvidersImpl(factory)
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFlickrApi(retrofit: Retrofit): FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }
}
