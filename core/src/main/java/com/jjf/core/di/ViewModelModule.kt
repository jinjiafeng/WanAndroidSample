package com.jjf.core.di

import androidx.lifecycle.ViewModelProvider
import com.jjf.template.util.lifecycle.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * @author xj
 * date: 18-11-8
 * description :
 */

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}
