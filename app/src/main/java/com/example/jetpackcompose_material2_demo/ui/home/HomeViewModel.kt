package com.example.jetpackcompose_material2_demo.ui.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose_material2_demo.data.model.ColorModel
import com.example.jetpackcompose_material2_demo.data.model.DropDownCategoryModel
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

    private val _searchViewState: MutableState<SearchViewState> =
        mutableStateOf(value = SearchViewState.CLOSED)
    val searchViewState: State<SearchViewState> = _searchViewState

    private val _searchTextState = mutableStateOf(value = "")
    val searchTextState = _searchTextState

    val tagChipList = mutableStateListOf(
        DropDownCategoryModel(imageVector = Icons.Outlined.List, title = "All"),
        DropDownCategoryModel(imageVector = Icons.Outlined.Person, title = "Miscellaneous"),
        DropDownCategoryModel(imageVector = Icons.Outlined.Timelapse, title = "To-do"),
        DropDownCategoryModel(imageVector = Icons.Outlined.Star, title = "Important"),
        DropDownCategoryModel(imageVector = Icons.Outlined.WorkOutline, title = "Work")
    )

    private val _selectedTagChip = mutableStateOf("")
    val selectedTagChip
        get() = _selectedTagChip

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

    private fun getSearchNotes(keyWord: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getSearchNotes(keyWord).distinctUntilChanged().collect { result ->
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

    private fun getNotesByTag(mTag: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getNotesByTag(mTag).distinctUntilChanged().collect { result ->
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

    private fun getFilteredNotes(keyWord: String, mTag: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getFilteredNotes(keyWord, mTag).distinctUntilChanged().collect { result ->
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
        if (_selectedTagChip.value == "") {
            if (newValue.isNotEmpty()) {
                getSearchNotes(_searchTextState.value)
            } else {
                getAllNotes()
            }
        }
        else {
            //todo handle both search + filter tag
            getFilteredNotes(_searchTextState.value, _selectedTagChip.value)
        }
    }

    fun selectTagChip(position: Int, model: DropDownCategoryModel) {
        for ((i, getModel) in tagChipList.withIndex()) {
            if (getModel.isSelected) {
                tagChipList[i].isSelected = false
            }

        }
        tagChipList[position] = model
        selectedTagChip.value = if (position > 0) {
            model.title
        } else {
            ""
        }

        if (_searchTextState.value.isEmpty()) {
            if (position == 0) {
                getAllNotes()
            } else {
                getNotesByTag(_selectedTagChip.value)
            }
        } else {
            //todo handle both search + filter tag
            if (position == 0) {
                getSearchNotes(_searchTextState.value)
            } else {
                getFilteredNotes(_searchTextState.value, _selectedTagChip.value)
            }
        }
    }
}