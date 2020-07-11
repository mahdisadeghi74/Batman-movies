package com.yara.project.batmanproject.di

import androidx.lifecycle.ViewModelProvider
import com.yara.project.batmanproject.viewmodel.ViewModelProvidersFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelProviderModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelsProvidersFactory: ViewModelProvidersFactory): ViewModelProvider.Factory
}