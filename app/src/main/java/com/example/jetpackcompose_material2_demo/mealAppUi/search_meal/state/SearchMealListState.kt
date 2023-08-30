package com.example.jetpackcompose_material2_demo.mealAppUi.search_meal.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpack_compose_demo.data.model.Meal
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category
import com.example.jetpackcompose_material2_demo.data.remoteModel.SearchMeal

sealed class SearchMealListState {
    object Empty : SearchMealListState()
    object Loading : SearchMealListState()
    data class Success(val list: SnapshotStateList<SearchMeal>) : SearchMealListState()
    data class Error(val errorMsg: String) : SearchMealListState()
}