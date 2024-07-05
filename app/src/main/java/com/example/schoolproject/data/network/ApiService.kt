package com.example.schoolproject.data.network

import com.example.schoolproject.data.network.model.ResponseListDto
import retrofit2.http.GET

interface ApiService {

    @GET("list")
    suspend fun loadTodoItemList(): ResponseListDto

}