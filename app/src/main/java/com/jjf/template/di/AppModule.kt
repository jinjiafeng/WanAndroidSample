package com.jjf.template.di

import android.content.Context
import androidx.room.Room
import com.jjf.core.di.HttpModule
import com.jjf.core.di.ViewModelModule
import com.jjf.template.App
import com.jjf.template.data.PreferenceStorage
import com.jjf.template.data.SharedPreferenceStorage
import com.jjf.template.data.api.ApiService
import com.jjf.template.data.db.AppDb
import com.jjf.template.data.db.ArticleDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author xj
 * date: 18-11-7
 * description :
 */

@Module(includes = [ViewModelModule::class,HttpModule::class])
class AppModule {
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providePreferenceStorage(context: Context): PreferenceStorage {
        return SharedPreferenceStorage(context)
    }

    @Singleton
    @Provides
    fun provideGithubService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(application: App): AppDb {
        return Room.databaseBuilder(application, AppDb::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: AppDb): ArticleDao {
        return db.articleDao()
    }
}
