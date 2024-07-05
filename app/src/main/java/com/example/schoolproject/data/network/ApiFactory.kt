package com.example.schoolproject.data.network

import com.yandex.authsdk.YandexAuthToken
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://hive.mrdekk.ru/todo/"

    private lateinit var token: YandexAuthToken

    fun initialize(token: YandexAuthToken) {
        this.token = token
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "OAuth ${token.value}")
                .build()
            chain.proceed(request)
        }
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiService = retrofit.create(ApiService::class.java)
}