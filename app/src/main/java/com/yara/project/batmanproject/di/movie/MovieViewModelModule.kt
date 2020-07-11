package com.yara.project.batmanproject.di.movie

import androidx.lifecycle.ViewModel
import com.yara.project.batmanproject.di.ViewModelKey
import com.yara.project.batmanproject.ui.MovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MovieViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindStoryViewModel(storyViewModel: MovieViewModel): ViewModel

}