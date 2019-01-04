package com.jjf.template.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jjf.template.ui.UserViewModel
import com.jjf.template.util.lifecycle.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author xj
 * date: 18-11-8
 * description :
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}
