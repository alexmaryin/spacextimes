package ru.alexmaryin.spacextimes_rx.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alexmaryin.spacextimes_rx.BuildConfig
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.api.RetrofitApiService
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApi
import ru.alexmaryin.spacextimes_rx.data.api.SpaceXApiImpl
import ru.alexmaryin.spacextimes_rx.data.api.SpacexUrls
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApiImpl
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderApi
import ru.alexmaryin.spacextimes_rx.data.api.wiki.WikiLoaderImpl
import ru.alexmaryin.spacextimes_rx.data.local.TranslateDatabase
import ru.alexmaryin.spacextimes_rx.data.repository.ApiLocal
import ru.alexmaryin.spacextimes_rx.data.repository.ApiLocalImpl
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManager
import ru.alexmaryin.spacextimes_rx.ui.adapters.ViewHoldersManagerImpl
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders.HeaderViewHolder
import ru.alexmaryin.spacextimes_rx.ui.adapters.ItemTypes
import ru.alexmaryin.spacextimes_rx.ui.adapters.recyclerViewHolders.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = SpacexUrls.Base

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient.Builder()
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

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RetrofitApiService = retrofit.create(RetrofitApiService::class.java)

    @Provides
    @Singleton
    fun provideApi(api: SpaceXApiImpl): SpaceXApi = api

    @Provides
    @Singleton
    fun provideLocalApi(api: ApiLocalImpl): ApiLocal = api

    @Provides
    fun provideTranslator(translator: TranslatorApiImpl): TranslatorApi = translator

    @Provides
    fun provideWikiApi(wikiApi: WikiLoaderImpl): WikiLoaderApi = wikiApi

    @Provides
    @Singleton
    fun provideAdaptersManager(): ViewHoldersManager = ViewHoldersManagerImpl().apply {
        registerViewHolder(ItemTypes.HEADER, HeaderViewHolder())
        registerViewHolder(ItemTypes.CAPSULE, CapsuleViewHolder())
        registerViewHolder(ItemTypes.CORE, CoreViewHolder())
        registerViewHolder(ItemTypes.CREW, CrewViewHolder())
        registerViewHolder(ItemTypes.DRAGON, DragonsViewHolder())
        registerViewHolder(ItemTypes.HISTORY_EVENT, HistoryEventsViewHolder())
        registerViewHolder(ItemTypes.LANDING_PAD, LandingPadViewHolder())
        registerViewHolder(ItemTypes.LAUNCH, LaunchesViewHolder())
        registerViewHolder(ItemTypes.LAUNCH_PAD, LaunchPadViewHolder())
        registerViewHolder(ItemTypes.ROCKET, RocketViewHolder())
    }
}