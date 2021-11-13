package com.example.secondhiltapp.di

import android.app.Application
import com.example.secondhiltapp.api.network.ApiSoccer
import com.example.secondhiltapp.db.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            //.client(getLoggingHttpClient())
            .baseUrl(ApiSoccer.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): ApiSoccer =
        retrofit.create(ApiSoccer::class.java)

    @Provides
    @Singleton
    fun getAppDB(context: Application) : AppRoom {
        return AppRoom.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun getSoccerDao(appDB: AppRoom) : SoccerDao {
        return appDB.getSoccerDao()
    }

    @Provides
    @Singleton
    fun getLanguageDao(appDB: AppRoom) : LanguageDao {
        return appDB.getLanguageDao()
    }

    @Provides
    @Singleton
    fun getBookmark(appDB: AppRoom) : BookmarkDao {
        return appDB.getBookmarkDao()
    }

    @Provides
    @Singleton
    fun getIsActiveDao(appDB: AppRoom) : IsActiveDao {
        return appDB.getIsActiveDao()
    }



    private fun getLoggingHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        return builder.build()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope