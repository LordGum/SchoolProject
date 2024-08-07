package com.example.schoolproject.data.network

import com.example.schoolproject.data.network.model.ResponseListDto
import com.example.schoolproject.data.network.model.ReturnElementDto
import com.example.schoolproject.data.network.model.ReturnElementListDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("list")
    suspend fun loadTodoItemList(): Response<ResponseListDto>

    @POST("list")
    suspend fun addTodoItem (
        @Header("X-Last-Known-Revision") revision: Int,
        @Body element: ReturnElementDto
    ): Response<ResponseListDto>

    @DELETE("list/{itemId}")
    suspend fun deleteTodoItem (
        @Path("itemId") itemId: String,
        @Header("X-Last-Known-Revision") revision: Int
    ): Response<ResponseListDto>

    @PUT("list/{itemId}")
    suspend fun refactorTodoItem (
        @Path("itemId") itemId: String,
        @Header("X-Last-Known-Revision") revision: Int,
        @Body element: ReturnElementDto
    ): Response<ResponseListDto>

    @PATCH("list")
    suspend fun updateTodoItemListOnService(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body list: ReturnElementListDto
    ): Response<ResponseListDto>
}