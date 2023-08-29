package com.example.jetpackcompose_material2_demo.mealAppUi.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category

sealed class HomeScreenState {
    object Empty : HomeScreenState()
    object Loading : HomeScreenState()
    data class Success(val list: SnapshotStateList<Category>) : HomeScreenState()
    data class Error(val errorMsg: String) : HomeScreenState()
}