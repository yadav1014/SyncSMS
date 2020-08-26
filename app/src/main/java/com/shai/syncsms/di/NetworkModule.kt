package com.shai.syncsms.di

import com.google.gson.Gson
import com.shai.syncsms.data.network.SmsApi
import com.shai.syncsms.util.AppConstants
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { Gson() }
    single { provideOkHttpClient() }
    single { provideForecastApi(get()) }
    single { provideRetrofit(get(), get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideForecastApi(retrofit: Retrofit): SmsApi = retrofit.create(SmsApi::class.java)