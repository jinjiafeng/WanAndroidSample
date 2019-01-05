package com.jjf.template.di

import com.jjf.template.di.scope.FragmentScoped
import com.jjf.template.ui.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author xj
 * date: 18-11-7
 * description :
 */
@Module
abstract class FragmentBuildersModule {



    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun ContributeMainFragment(): MainFragment


}
