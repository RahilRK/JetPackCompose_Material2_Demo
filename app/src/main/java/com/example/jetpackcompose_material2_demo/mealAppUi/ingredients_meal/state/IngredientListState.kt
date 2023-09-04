package com.example.jetpackcompose_material2_demo.mealAppUi.ingredients_meal.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpackcompose_material2_demo.data.remoteModel.Area
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category
import com.example.jetpackcompose_material2_demo.data.remoteModel.Ingredient

sealed class IngredientListState {
    object Empty : IngredientListState()
    object Loading : IngredientListState()
    data class Success(val list: SnapshotStateList<Ingredient>) : IngredientListState()
    data class Error(val errorMsg: String) : IngredientListState()
}