package com.example.schoolproject.data.network

import android.util.Log
import com.yandex.authsdk.YandexAuthToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://hive.mrdekk.ru/todo/"

    private  var token: YandexAuthToken? = null

    fun initialize(token: YandexAuthToken?) {
        this.token = token
        Log.d("tag", "token = ${token?.value ?: "Lalaith"}")
        Log.d("tag", "time = ${token?.expiresIn ?: 0}")
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain -> auth(chain) }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private fun auth(chain: Interceptor.Chain): okhttp3.Response {
        val request: Request = if(token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "OAuth ${token!!.value}")
                .build()
        } else {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer Lalaith")
                .build()
        }
        return chain.proceed(request)
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiService = retrofit.create(ApiService::class.java)
}