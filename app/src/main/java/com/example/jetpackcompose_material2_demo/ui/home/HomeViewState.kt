package com.example.jetpackcompose_material2_demo.ui.home

import com.example.jetpackcompose_material2_demo.data.model.NoteModel

sealed class HomeViewState {
    // Represents different states for the All Task screen
    object Empty : HomeViewState()
    object Loading : HomeViewState()
    data class Success(val task: List<NoteModel>) : HomeViewState()
    data class Error(val exception: Throwable) : HomeViewState()
}