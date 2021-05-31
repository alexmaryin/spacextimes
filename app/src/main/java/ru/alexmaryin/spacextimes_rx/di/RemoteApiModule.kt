package ru.alexmaryin.spacextimes_rx.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.alexmaryin.spacextimes_rx.BuildConfig
import ru.alexmaryin.spacextimes_rx.data.api.remote.ApiRemote
import ru.alexmaryin.spacextimes_rx.data.api.remote.SpacexUrls
import ru.alexmaryin.spacextimes_rx.data.api.translator.ApiFastTranslator
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorUrls
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TranslatorUrl

@Module
@InstallIn(SingletonComponent::class)
class RemoteApiModule {

    private val cacheSize = 10 * 1024 * 1024

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

    private val moshi = Moshi.Builder()
        .add(DateJsonAdapter())
        .build()

    @BaseUrl
    @Provides
    @Singleton
    fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(SpacexUrls.BaseUrl)
            .client(okHttpClient)
            .build()

    @TranslatorUrl
    @Provides
    @Singleton
    fun provideTranslatorRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(TranslatorUrls.BaseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(@BaseUrl retrofit: Retrofit): ApiRemote = retrofit.create(ApiRemote::class.java)

    @Provides
    @Singleton
    fun provideApiFastTranslator(@TranslatorUrl retrofit: Retrofit): ApiFastTranslator = retrofit.create(ApiFastTranslator::class.java)
}