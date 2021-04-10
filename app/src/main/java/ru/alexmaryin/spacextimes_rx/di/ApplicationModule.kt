package ru.alexmaryin.spacextimes_rx.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexmaryin.spacextimes_rx.BuildConfig
import ru.alexmaryin.spacextimes_rx.data.api.RetrofitApiService
import ru.alexmaryin.spacextimes_rx.data.api.SpacexUrls
import ru.alexmaryin.spacextimes_rx.data.local.TranslateDatabase
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    private val cacheSize = 10 * 1024 * 1024

    @Provides
    fun provideBaseUrl() = SpacexUrls.Base

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context) = if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(Cache(context.cacheDir, cacheSize.toLong()))
                .build()
        } else OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, cacheSize.toLong()))
            .build()

    private val gsonBuilder = GsonBuilder()
        .registerTypeAdapter(Date::class.java, DateJsonAdapter)
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RetrofitApiService = retrofit.create(RetrofitApiService::class.java)

    @Provides
    @Singleton
    fun provideTranslationsDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            TranslateDatabase::class.java,
            "translationsDb"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTranslationsDao(db: TranslateDatabase) = db.dao
}