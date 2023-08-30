package com.example.jetpackcompose_material2_demo.mealAppUi.home.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jetpackcompose_material2_demo.data.remoteModel.Category

sealed class CategoryListState {
    object Empty : CategoryListState()
    object Loading : CategoryListState()
    data class Success(val list: SnapshotStateList<Category>) : CategoryListState()
    data class Error(val errorMsg: String) : CategoryListState()
}