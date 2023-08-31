package com.example.jetpackcompose_material2_demo.mealAppUi.country_meal.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpackcompose_material2_demo.data.remoteModel.Area
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category

sealed class AreaListState {
    object Empty : AreaListState()
    object Loading : AreaListState()
    data class Success(val list: SnapshotStateList<Area>) : AreaListState()
    data class Error(val errorMsg: String) : AreaListState()
}