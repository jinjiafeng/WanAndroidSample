package com.jjf.template.di

import android.content.Context
import androidx.room.Room
import com.jjf.template.App
import com.jjf.template.BuildConfig
import com.jjf.template.data.PreferenceStorage
import com.jjf.template.data.SharedPreferenceStorage
import com.jjf.template.data.api.ApiService
import com.jjf.template.data.db.AppDb
import com.jjf.template.data.db.ArticleDao
import com.jjf.template.util.lifecycle.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author xj
 * date: 18-11-7
 * description :
 */

@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providePreferenceStorage(context: Context):PreferenceStorage{
        return SharedPreferenceStorage(context)
    }

    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(loggingInterceptor)
        }
        builder.connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
        return builder.build()
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
