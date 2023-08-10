package com.example.jetpackcompose_material2_demo.ui.update_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import com.example.jetpackcompose_material2_demo.ui.home.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateNoteViewModel @Inject constructor(
    private val repository: MainRepository,
    private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

    private val _noteModel = MutableStateFlow<UpdateNoteState>(UpdateNoteState.Loading)
    val noteModel: StateFlow<UpdateNoteState>
        get() = _noteModel

    init {
        getNotesDetail()
    }
    private fun getNotesDetail() = viewModelScope.launch(Dispatchers.IO) {
        val id = savedStateHandle.get<String>("id") ?: "-1"
        repository.getNotesDetail(id.toInt()).distinctUntilChanged().collect { result ->
            try {
                _noteModel.value = UpdateNoteState.Success(result)
            } catch (e: Exception) {
                _noteModel.value = UpdateNoteState.Error(e)
            }
        }
    }

    fun updateNote(noteModel: NoteModel): Flow<Long> =
        flow {
            //do long work
            val id = repository.updateNote(noteModel)
            emit(id)
        }.flowOn(Dispatchers.IO)

}