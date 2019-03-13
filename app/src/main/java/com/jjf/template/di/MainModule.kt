package com.jjf.template.di

import androidx.lifecycle.ViewModel
import com.jjf.template.di.scope.FragmentScoped
import com.jjf.template.ui.HomeFragment
import com.jjf.template.ui.HomeViewModel
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
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun ContributeHomeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

}
