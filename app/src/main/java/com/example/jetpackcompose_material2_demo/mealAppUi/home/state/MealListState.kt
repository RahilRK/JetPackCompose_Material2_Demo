package com.example.jetpackcompose_material2_demo.mealAppUi.home.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpackcompose_material2_demo.data.remoteModel.Meal

sealed class MealListState {
    object Empty : MealListState()
    object Loading : MealListState()
    data class Success(val list: SnapshotStateList<Meal>) : MealListState()
    data class Error(val errorMsg: String) : MealListState()
}