package com.example.jetpackcompose_material2_demo.mealAppUi.meal_detail.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpackcompose_material2_demo.data.remoteModel.Meal
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealDetail
import com.example.jetpackcompose_material2_demo.data.remoteModel.MealX

sealed class MealDetailState {
    object Empty : MealDetailState()
    object Loading : MealDetailState()
    data class Success(val list: SnapshotStateList<MealX>) : MealDetailState()
    data class Error(val errorMsg: String) : MealDetailState()
}