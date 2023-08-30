package com.example.jetpackcompose_material2_demo.data.remote

import com.example.jetpack_compose_demo.data.model.MealList
import com.example.jetpackcompose_material2_demo.data.remoteModel.CategoryList
import com.example.jetpackcompose_material2_demo.data.remoteModel.SearchMealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/json/v1/1/categories.php")
    suspend fun getCategoryList(): Response<CategoryList>

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealList(
        @Query("c") strCategory: String,
    ): Response<MealList>

    @GET("api/json/v1/1/search.php")
    suspend fun getSearchMealList(
        @Query("s") keyWord: String,
    ): Response<SearchMealList>
}