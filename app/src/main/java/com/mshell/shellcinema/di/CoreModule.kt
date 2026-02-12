package com.mshell.shellcinema.di

import com.mshell.shellcinema.BuildConfig
import com.mshell.shellcinema.core.data.ShellCinemaRepositoryImpl
import com.mshell.shellcinema.core.data.source.remote.Api
import com.mshell.shellcinema.core.data.source.remote.RemoteDataSource
import com.mshell.shellcinema.core.domain.repository.ShellCinemaRepository
import com.mshell.shellcinema.core.utils.ApiInterceptor
import com.mshell.shellcinema.core.utils.NetworkInfo.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.also
import kotlin.apply
import kotlin.jvm.java

val networkModule = module {
    // OkHttp Client
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .also {
                if (BuildConfig.DEBUG) it.addInterceptor(logging)
            }
            .addInterceptor(ApiInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()


    }

    // Retrofit
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(get())
            .build()
    }

    // API Service
    single<Api> {
        get<Retrofit>().create(Api::class.java)
    }
}

val repositoryModule = module {
    // Remote Data Source
    single { RemoteDataSource(get()) }

    // Repository
    single<ShellCinemaRepository> {
        ShellCinemaRepositoryImpl(get())
    }
}
