package com.example.ubb_pdm_android.todo.data.remote

import retrofit2.http.*
import com.example.ubb_pdm_android.core.Api
import com.example.ubb_pdm_android.todo.data.Item

object ItemApi {
    interface Service {
        @GET("/api/item")
        suspend fun find(): List<Item>

        @GET("/api/item/{id}")
        suspend fun read(@Path("id") itemId: String): Item;

        @Headers("Content-Type: application/json")
        @POST("/api/item")
        suspend fun create(@Body item: Item): Item

        @Headers("Content-Type: application/json")
        @PUT("/api/item/{id}")
        suspend fun update(@Path("id") itemId: String, @Body item: Item): Item

        @DELETE("/api/item/{id}")
        suspend fun delete(@Path("id") itemId: String): Item

    }

    val service: Service = Api.retrofit.create(Service::class.java)
}