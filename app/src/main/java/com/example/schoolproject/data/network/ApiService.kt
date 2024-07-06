package com.example.schoolproject.data.network

import com.example.schoolproject.data.network.model.ResponseListDto
import com.example.schoolproject.data.network.model.ReturnElementDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("list")
    suspend fun loadTodoItemList(): ResponseListDto

    @POST("list")
    suspend fun addTodoItem (
        @Header("X-Last-Known-Revision") revision: Int,
        @Body element: ReturnElementDto
    )

    @DELETE("list/{itemId}")
    suspend fun deleteTodoItem (
        @Path("itemId") itemId: String,
        @Header("X-Last-Known-Revision") revision: Int
    )
}