package com.example.jetpackcompose_material2_demo.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.model.NoteModel
import com.example.jetpackcompose_material2_demo.repository.MainRepository
import com.example.jetpackcompose_material2_demo.ui.home.searchView.SearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _list = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val list: StateFlow<HomeViewState>
        get() = _list

    private val _searchViewState: MutableState<SearchViewState> = mutableStateOf(value = SearchViewState.CLOSED)
    val searchViewState: State<SearchViewState> = _searchViewState

    private val _searchTextState = mutableStateOf(value = "")
    val searchTextState = _searchTextState

    init {
        getAllNotes()
    }

    private fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAllNotes().distinctUntilChanged().collect { result ->
            try {
                if (result.isEmpty()) {
                    _list.value = HomeViewState.Empty
                } else {
                    _list.value = HomeViewState.Success(result.toMutableList())
                }
            } catch (e: Exception) {
                _list.value = HomeViewState.Error(e)
            }
        }
    }

    fun deleteNote(id: Int): Flow<Int> =
        flow {
            //do long work
            val id = repository.deleteNews(id)
            emit(id)
        }.flowOn(Dispatchers.IO)

    fun updateSearchViewState(newValue: SearchViewState) {
        _searchViewState.value = newValue
    }
    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}