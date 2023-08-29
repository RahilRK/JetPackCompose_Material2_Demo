package com.example.jetpackcompose_material2_demo.data.remote

import com.example.jetpackcompose_material2_demo.data.remoteModel.CategoryList
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("api/json/v1/1/categories.php")
    suspend fun getCategoryList(): Response<CategoryList>

/*    @GET("api/json/v1/1/filter.php")
    suspend fun getMealList(
        @Query("c") strCategory: String,
    ): Response<MealList>*/
}