package com.jjf.template.di

import androidx.lifecycle.ViewModel
import com.jjf.template.ui.MainFragment
import com.jjf.template.ui.UserViewModel
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
abstract class FragmentBuildersModule {


    @ContributesAndroidInjector
     abstract fun ContributeMainFragment(): MainFragment


    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
     abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel
}
