package com.jjf.template.ui.home

import com.jjf.template.di.scope.ChildFragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author xj
 * date: 19-3-14
 * description :
 */

@Module
abstract class CategoryModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    abstract fun ContributeCategoryFragment(): CategoryFragment

}
