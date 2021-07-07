package com.example.bestandroidcode.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bestandroidcode.di.annotation.ViewModelKey
import com.example.bestandroidcode.viewModel.MyFavoriteViewModel
import com.example.bestandroidcode.viewModel.TheCatViewModel
import com.example.bestandroidcode.viewModel.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Kuan Wah Teo on 07/11/2020
 **/

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TheCatViewModel::class)
    abstract fun bindTheCatViewModel(theCatViewModel: TheCatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyFavoriteViewModel::class)
    abstract fun bindMyFavoriteViewModel(myFavoriteViewModel: MyFavoriteViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}