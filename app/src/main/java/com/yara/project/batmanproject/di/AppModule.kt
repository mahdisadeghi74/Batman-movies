package com.yara.project.batmanproject.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.room.Room
import com.yara.project.batmanproject.database.MainDataBase
import com.yara.project.batmanproject.network.MovieApi
import com.yara.project.batmanproject.ui.adapter.MoviesAdapter
import com.yara.project.batmanproject.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun providerMainDataBaseInstance(application: Application): MainDataBase{
        return Room.databaseBuilder(application, MainDataBase::class.java, "com.yara.batman.dbvhichbh").
            allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providerRetrofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providersMovieApi(retrofit: Retrofit): MovieApi{
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun providersMovieAdapter(): MoviesAdapter{
        return MoviesAdapter()
    }
}