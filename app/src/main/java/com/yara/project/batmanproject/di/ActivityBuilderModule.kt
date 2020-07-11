package com.yara.project.batmanproject.di

import com.yara.project.batmanproject.di.movie.MovieViewModelModule
import com.yara.project.batmanproject.ui.activity.MainActivity
import com.yara.project.batmanproject.ui.activity.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [MovieViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MovieViewModelModule::class])
    abstract fun contributeMainDetailActivity(): MovieDetailActivity
}