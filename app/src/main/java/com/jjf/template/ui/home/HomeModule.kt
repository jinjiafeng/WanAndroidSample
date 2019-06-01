package com.jjf.template.ui.home

import androidx.lifecycle.ViewModel
import com.jjf.template.di.ViewModelKey
import com.jjf.template.di.scope.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * @author xj
 * date: 18-11-7
 * description :
 */
@Module
abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [CategoryModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(categoryViewModel: CategoryViewModel): ViewModel
}
