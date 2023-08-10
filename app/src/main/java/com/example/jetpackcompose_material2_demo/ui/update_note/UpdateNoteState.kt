package com.example.jetpackcompose_material2_demo.ui.update_note

import com.example.jetpackcompose_material2_demo.data.model.NoteModel

sealed class UpdateNoteState {
    // Represents different states for the All Task screen
    object Loading : UpdateNoteState()
    data class Success(val model: NoteModel) : UpdateNoteState()
    data class Error(val exception: Throwable) : UpdateNoteState()
}